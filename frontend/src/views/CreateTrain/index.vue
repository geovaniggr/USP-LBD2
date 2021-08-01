<template>
  <Header />
  <div class="container-wrapper w-full">
    <h1 class="text-brand-primary text-3xl font-bold mt-8 uppercase text-center">Buscar Aluno</h1>
    <input list="clients" v-model="state.selected.email" class="block rounded px-8 py-4 w-1/2 mt-8 mx-auto" />
    <button @click="buscarAluno()" class="text-lg px-8 rounded rounded-md bg-brand-primary">Buscar Aluno</button>
    <datalist id="clients">
      <option v-for="(usr, index) of state.client" :key="index" :value="usr.email">{{ usr.nome }} -- {{ usr.email }}</option>
    </datalist>
    <section>
      <div class="mt-8">
        <header class="rounded bg-brand-primary w-full h-16 px-8 flex items-center justify-between">
          <h3 class="text-3xl uppercase font-bold">Último Treino</h3>
          <button class="border-0 text-5xl font-bold" @click="toggleLastTrainVisibility">X</button>
        </header>
        <div v-if="state.showLastTrain">
          <ul class="flex flex-wrap gap-4 mt-8 justify-center">
            <li v-for="(exercicio, index) of data.exercicios" :key="index" class="bg-black rounded p-8 text-center">
              <span class="block font-bold text-lg text-white mb-8">{{ exercicio.nome }}</span>
              <div class="flex justify-between">
                <span class="block font-bold text-lg text-brand-primary">Exercicio</span>
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
    </section>
    <div class="mt-8">
      <header class="rounded bg-brand-primary w-full h-16 px-8 flex items-center justify-between">
        <h3 class="text-3xl uppercase font-bold">Novo Treino</h3>
      </header>
      <div>
        <form @submit="createExercise">
          <li v-for="(input, i) of data.formulario.splice(0, 20)" :key="i" class="grid grid-cols-5 grid-row-1 items-center my-8 gap-4">
            <span class="text-white text-2xl"> {{ input.nome }}</span>
            <div class="px-2 py-4 rounded flex flex-col justify-center items-center bg-black h-full">
              <label class="text-brand-primary text-2xl mb-4"> Adicionar </label>
              <input type="checkbox" class="w-1/4 h-1/4" v-model="input.selected" />
            </div>
            <div class="px-2 py-4 rounded flex flex-col items-center bg-black">
              <label class="text-brand-primary text-2xl mb-4"> Series </label>
              <input type="text" class="w-2/4 rounded px-4 py-2 text-center" v-model="input.series" />
            </div>
            <div class="px-2 py-4 rounded flex flex-col items-center bg-black">
              <label class="text-brand-primary text-2xl mb-4"> Carga </label>
              <input type="text" class="w-2/4 rounded px-4 py-2 text-center" v-model="input.carga" />
            </div>
            <div class="px-2 py-4 rounded flex flex-col items-center bg-black">
              <label class="text-brand-primary text-2xl mb-4"> Repetição </label>
              <input type="text" class="w-2/4 rounded px-4 py-2 text-center" v-model="input.repeticao" />
            </div>
          </li>
          <button type="submit" class="py-8 px-12 rounded rounded-md bg-brand-primary uppercase text-2xl text-bold">Finalizar</button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import Header from '@/components/Header'
import { onMounted, reactive } from 'vue'

export default {
  components: {
    Header,
  },
  setup() {
    const data = reactive({
      exercicios: [
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
        { nome: 'Abdominal', carga: '50kg', repeticoes: 30, series: 3 },
      ],
      formulario: [
        { nome: 'Abdominal', selected: false, carga: '0', repeticao: 0, series: 0 },
        { nome: 'Abdominal', selected: false, carga: '0', repeticao: 0, series: 0 },
        { nome: 'Abdominal', selected: false, carga: '0', repeticao: 0, series: 0 },
        { nome: 'Abdominal', selected: false, carga: '0', repeticao: 0, series: 0 },
        { nome: 'Abdominal', selected: false, carga: '0', repeticao: 0, series: 0 },
        { nome: 'Abdominal', selected: false, carga: '0', repeticao: 0, series: 0 },
        { nome: 'Abdominal', selected: false, carga: '0', repeticao: 0, series: 0 },
      ],
    })

    function buscarExercicios() {
      fetch('http://localhost:8080/api/exercises')
        .then((raw) => raw.json())
        .then((exercises) => {
          const final = exercises.map((exercise) => ({ id: exercise.id, nome: exercise.nome, selected: false, carga: '0', repeticao: 0, series: 0 }))
          console.log(final)
          data.formulario = final
        })
    }

    onMounted(() => buscarExercicios())

    const state = reactive({
      selected: {},
      showLastTrain: false,
      train: [],
      client: [
        { nome: 'Geovani Granieri', email: 'geovani.granieri@usp.br' },
        { nome: 'Mc Hariel', email: 'hariel@mc.br' },
        { nome: 'Richard Rasmussen', email: 'richard@bio.br' },
      ],
    })

    function toggleLastTrainVisibility() {
      state.showLastTrain = !state.showLastTrain
    }

    function createExercise(e) {
      e.preventDefault()
      console.log(JSON.stringify(data.formulario))
    }

    function buscarUltimoTreino() {
      fetch('http://localhost:8080/api/trains/user/4')
        .then((raw) => raw.json())
        .then((exercicios) => {
          const ultimo = exercicios.length - 1

          data.exercicios = exercicios[ultimo].exercicios.splice(1, 10)
        })
    }

    function buscarAluno() {
      fetch(`http://localhost:8080/api/users/email/${state.selected.email}`)
        .then((raw) => raw.json())
        .then((user) => {
          const resultado = user != null ? { nome: user.nome, email: user.email } : { nome: 'Sem Resultados', email: 'Sem Resultados' }
          state.client = [resultado]
        })
      buscarExercicios()
      buscarUltimoTreino()
    }

    const client = [
      { nome: 'Geovani Granieri', email: 'geovani.granieri@usp.br' },
      { nome: 'Mc Hariel', email: 'hariel@mc.br' },
      { nome: 'Richard Rasmussen', email: 'richard@bio.br' },
    ]

    return {
      state,
      data,
      client,
      toggleLastTrainVisibility,
      createExercise,
      buscarAluno,
    }
  },
}
</script>
