<template>
  <main>
    <Header />
    <div class="container-wrapper">
      <div class="base-layout">
        <div>
          <h1 class="text-white uppercase font-base text-3xl mt-8 text-center">Treinos</h1>
          <ul class="flex flex-col gap-4 mt-8 max-h-96 overflow-scroll">
            <li v-for="(treino, index) of state.treinos" :key="index" class="bg-black cursor-pointer rounded p-8 border border-brand-primary" @click="updateTreino(index)">
              <span class="block font-bold text-lg text-white text-center mb-4"> Treino {{ index }}</span>
              <div class="flex justify-between">
                <span class="font-bold text-lg text-brand-primary mr-6"> Data Inicio</span>
                <span class="font-bold text-lg text-white">{{ treino.dataInicio }}</span>
              </div>
              <div class="flex justify-between">
                <span class="font-bold text-lg text-brand-primary mr-6"> Data Fim</span>
                <span class="font-bold text-lg text-white">{{ treino.dataFim }}</span>
              </div>
            </li>
          </ul>
        </div>
        <div>
          <h1 class="text-white uppercase font-base text-3xl mt-8 text-center">Exercícios</h1>
          <ul class="flex flex-wrap gap-4 mt-8 justify-center">
            <li v-for="(exercicio, index) of state.exercicios.splice(0, 10)" :key="index" class="bg-black rounded p-8 text-center">
              <span class="block font-bold text-lg text-white mb-8">{{ exercicio.nome }}</span>
              <div class="flex justify-between">
                <span class="block font-bold text-lg text-brand-primary">Carga</span>
                <span class="block font-bold text-lg text-white ml-8">{{ exercicio.carga }}</span>
              </div>
              <div class="flex justify-between">
                <span class="block font-bold text-lg text-brand-primary">Repetições</span>
                <span class="block font-bold text-lg text-white ml-8">{{ exercicio.repeticoes }}</span>
              </div>
              <div class="flex justify-between">
                <span class="block font-bold text-lg text-brand-primary">Series</span>
                <span class="block font-bold text-lg text-white ml-8">{{ exercicio.series }}</span>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </main>
</template>
<script>
import { onMounted, reactive } from 'vue'
import Header from '@/components/Header'
export default {
  components: {
    Header,
  },
  setup() {
    const state = reactive({
      exercicios: [
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
      ],
      treinos: [
        { dataInicio: parseDate(new Date()), dataFim: parseDate(new Date()) },
        { dataInicio: parseDate(new Date()), dataFim: parseDate(new Date()) },
        { dataInicio: parseDate(new Date()), dataFim: parseDate(new Date()) },
        { dataInicio: parseDate(new Date()), dataFim: parseDate(new Date()) },
        { dataInicio: parseDate(new Date()), dataFim: parseDate(new Date()) },
      ],
    })

    onMounted(function getDataFromAPI() {
      fetch('http://localhost:8080/api/trains/user/4')
        .then((raw) => raw.json())
        .then((response) => {
          state.treinos = response
          state.exercicios = response[0].exercicios
        })
    })

    function parseDate(date) {
      return Intl.DateTimeFormat('pt-BR').format(date)
    }

    function updateTreino(index) {
      state.exercicios = state.treinos[index].exercicios
    }

    return {
      state,
      updateTreino,
    }
  },
}
</script>
<style lang="postcss">
.base-layout {
  display: grid;
  grid-template-columns: 350px 1fr;
  gap: 2rem;
}
</style>
