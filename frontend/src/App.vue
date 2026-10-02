<script setup>
import { onMounted, ref } from "vue";

const cep = ref("");
const resultado = ref(null);
const historico = ref([]);
const consultando = ref(false);
const mensagem = ref("");
const mensagemTipo = ref("");
const erroHistorico = ref(false);

function formatarData(valor) {
  if (!valor) return "—";

  const data = new Date(valor);
  if (Number.isNaN(data.getTime())) return valor;

  return new Intl.DateTimeFormat("pt-BR", {
    dateStyle: "short",
    timeStyle: "short"
  }).format(data);
}

async function buscarHistorico() {
  try {
    const resposta = await fetch("/consultas", {
      headers: { Accept: "application/json" }
    });
    if (!resposta.ok) throw new Error("Falha ao carregar histórico");

    historico.value = await resposta.json();
    erroHistorico.value = false;
  } catch {
    erroHistorico.value = true;
  }
}

function mensagemDeErro(status) {
  if (status === 400) return "Informe um CEP válido.";
  if (status === 404) return "CEP não encontrado.";
  if (status === 502) return "Não foi possível consultar o serviço de CEP no momento.";
  return "Não foi possível realizar a consulta.";
}

async function consultarCep() {
  if (consultando.value) return;

  mensagem.value = "";
  mensagemTipo.value = "";
  const cepNormalizado = cep.value.replace(/\D/g, "");

  if (cepNormalizado.length !== 8) {
    mensagem.value = "Informe um CEP válido.";
    mensagemTipo.value = "erro";
    return;
  }

  consultando.value = true;
  try {
    const resposta = await fetch(`/consultas?cep=${encodeURIComponent(cepNormalizado)}`, {
      headers: { Accept: "application/json" }
    });
    if (!resposta.ok) {
      mensagem.value = mensagemDeErro(resposta.status);
      mensagemTipo.value = "erro";
      return;
    }

    resultado.value = await resposta.json();
    mensagem.value = "Consulta realizada com sucesso.";
    mensagemTipo.value = "sucesso";
    await buscarHistorico();
  } catch {
    mensagem.value = "Não foi possível realizar a consulta.";
    mensagemTipo.value = "erro";
  } finally {
    consultando.value = false;
  }
}

onMounted(buscarHistorico);
</script>

<template>
  <main class="pagina">
    <header class="cabecalho">
      <p class="rotulo">Consulta de endereço</p>
      <h1>Consulta de CEP</h1>
      <p class="subtitulo">Consulte um endereço pelo CEP.</p>
    </header>

    <section class="cartao busca" aria-labelledby="titulo-busca">
      <h2 id="titulo-busca">Consultar endereço</h2>
      <form class="formulario" @submit.prevent="consultarCep">
        <label for="cep">CEP</label>
        <div class="controles">
          <input
            id="cep"
            v-model="cep"
            name="cep"
            type="text"
            inputmode="numeric"
            autocomplete="postal-code"
            placeholder="01001-000"
            maxlength="9"
          />
          <button type="submit" :disabled="consultando">
            {{ consultando ? "Consultando..." : "Consultar" }}
          </button>
        </div>
        <p v-if="mensagem" class="mensagem" :class="mensagemTipo" role="status" aria-live="polite">
          {{ mensagem }}
        </p>
      </form>
    </section>

    <section v-if="resultado" class="cartao" aria-labelledby="titulo-resultado">
      <p class="rotulo">Consulta realizada</p>
      <h2 id="titulo-resultado">Resultado da consulta</h2>
      <dl class="dados resultado">
        <div>
          <dt>CEP</dt>
          <dd>{{ resultado.cep || "—" }}</dd>
        </div>
        <div>
          <dt>Logradouro</dt>
          <dd>{{ resultado.logradouro || "—" }}</dd>
        </div>
        <div>
          <dt>Bairro</dt>
          <dd>{{ resultado.bairro || "—" }}</dd>
        </div>
        <div>
          <dt>Cidade</dt>
          <dd>{{ resultado.cidade || "—" }}</dd>
        </div>
        <div>
          <dt>Data da consulta</dt>
          <dd>{{ formatarData(resultado.dataConsulta) }}</dd>
        </div>
      </dl>
    </section>

    <section class="cartao" aria-labelledby="titulo-historico">
      <p class="rotulo">Consultas realizadas</p>
      <h2 id="titulo-historico">Histórico de consultas</h2>

      <p v-if="erroHistorico" class="estado-vazio" role="status">
        Não foi possível carregar o histórico.
      </p>
      <p v-else-if="historico.length === 0" class="estado-vazio">
        Você ainda não realizou nenhuma consulta.
      </p>
      <div v-else class="tabela-container">
        <table>
          <thead>
            <tr>
              <th scope="col">CEP</th>
              <th scope="col">Logradouro</th>
              <th scope="col">Bairro</th>
              <th scope="col">Cidade</th>
              <th scope="col">Data da consulta</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(consulta, indice) in historico" :key="`${consulta.cep}-${consulta.dataConsulta}-${indice}`">
              <td>{{ consulta.cep }}</td>
              <td>{{ consulta.logradouro || "—" }}</td>
              <td>{{ consulta.bairro || "—" }}</td>
              <td>{{ consulta.cidade || "—" }}</td>
              <td>{{ formatarData(consulta.dataConsulta) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </main>
</template>
