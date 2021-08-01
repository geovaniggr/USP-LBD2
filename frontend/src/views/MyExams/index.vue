<template>
  <Header />
  <div class="container-wrapper">
    <div class="base-layout">
      <div>
        <h1 class="text-white uppercase font-base text-3xl mt-8 text-center">Avaliações</h1>
        <ul class="flex flex-col gap-4 mt-8 max-h-[450px] overflow-scroll">
          <li v-for="(e, index) of state.exams" :key="index" class="bg-black cursor-pointer rounded p-8 border border-brand-primary" @click="toggleActiveExam(index)">
            <div class="flex justify-between">
              <span class="block font-bold text-lg text-brand-primary">Data de Avaliação</span>
              <span class="block font-bold text-lg text-white ml-8">{{ e.dataAvalicao }}</span>
            </div>
          </li>
        </ul>
      </div>
      <div class="flex flex-col items-center">
        <h1 class="text-white uppercase font-base text-3xl my-8 text-center">Meus Resultados</h1>
        <ul class="flex gap-4">
          <li class="w-44 h-24 bg-black flex flex-col items-center justify-center border rounded-md border-brand-primary">
            <span class="block text-brand-primary font-bold text-lg"> Massa Magra </span>
            <span class="text-white text-lg"> {{ state.selected.massaMagra }} </span>
          </li>
          <li class="w-44 h-24 bg-black flex flex-col items-center justify-center border rounded-md border-brand-primary">
            <span class="block text-brand-primary font-bold text-lg"> IMC</span>
            <span class="text-white text-lg"> {{ state.selected.imc }} </span>
          </li>
          <li class="w-44 h-24 bg-black flex flex-col items-center justify-center border rounded-md border-brand-primary">
            <span class="block text-brand-primary font-bold text-lg"> Gordura Corporal </span>
            <span class="text-white text-lg"> {{ state.selected.gorduraCorporal }} </span>
          </li>
          <li class="w-44 h-24 bg-black flex flex-col items-center justify-center border rounded-md border-brand-primary">
            <span class="block text-brand-primary font-bold text-lg"> Gordura Visceral </span>
            <span class="text-white text-lg"> {{ state.selected.gorduraVisceral }} </span>
          </li>
        </ul>
        <h1 class="text-white uppercase font-base text-3xl my-8 text-center">Gráfico de Medidas</h1>
        <div v-for="chart of chartData" class="h-64 w-[500px] my-12 self-start p-8" :key="chart.id">
          <h1 class="text-2xl my-4 font-bold text-brand-primary">{{ chart.title }}</h1>
          <Chart :id="chart.id" type="line" :data="chart.data" :ref="chart.ref" :options="baseOptions" />
        </div>
      </div>
    </div>
  </div>
</template>
<style>
#doughnut {
  height: 300px;
  width: 300px;
}
</style>

<script>
import Header from '@/components/Header'
import Chart from '@/lib/graphic/Chart'

import { onMounted, reactive, ref } from 'vue'

export default {
  components: {
    Header,
    Chart,
  },
  setup() {
    // const chartRef = ref(null)

    let chartData

    const baseOptions = {
      scales: {
        x: {
          grid: {
            color: '#bebebe',
          },
          ticks: {
            color: '#bebebe',
          },
        },
        y: {
          grid: {
            color: '#bebebe',
          },
          ticks: {
            color: '#bebebe',
          },
        },
      },
    }

    function format(date) {
      return new Intl.DateTimeFormat('pt-BR').format(date)
    }

    const state = reactive({
      selected: {
        massaMagra: 12.8,
        imc: 23.54,
        gorduraCorporal: 15.32,
        aguaCorporal: 19.85,
        gorduraVisceral: 3.1,
        dataAvalicao: parseDate(new Date('2021-06-17')),
      },
      exams: [],
      chart: {
        massaMagra: [],
        aguaCorporal: [],
        gorduraCorporal: [],
        gorduraVisceral: [],
        imc: [],
      },
    })

    function createChart(exams) {
      const data = {
        massaMagra: [],
        aguaCorporal: [],
        gorduraCorporal: [],
        gorduraVisceral: [],
        imc: [],
      }

      console.log(exams)

      exams.forEach((exam) => {
        Object.entries(exam).forEach(([key, value]) => data[key]?.push(value))
      })

      state.chart = data
    }

    function update() {
      console.log('UHDSAHUDHUSADHUSADHUSAHU')
      chartData.forEach((chart) => {
        console.log(JSON.stringify(state.chart[chart.id]))
        chart.data = state.chart[chart.id]
        chart.ref.value.update(250)
      })
    }

    function toggleActiveExam(index) {
      state.selected = state.exams[index]
    }

    function fetchData() {
      fetch('http://localhost:8080/api/exams/4')
        .then((raw) => raw.json())
        .then((data) => {
          state.selected = data[0]
          state.exams = data
          createChart(data)
        })
        .then(() => {
          chartData.forEach((chart) => {
            chart.data.labels = state.chart[chart.id].map((v, i) => `Exame ${i + 1}`)
            chart.data.datasets[0].data = state.chart[chart.id]
            chart.ref.value.update(250)
          })
        })
    }

    chartData = [
      {
        title: 'Massa Magra',
        ref: ref(null),
        id: 'massaMagra',
        color: 'red',
        data: {
          labels: state.chart.massaMagra.map((v, i) => `Exame ${i}`),
          datasets: [{ label: '%', data: state.chart.massaMagra, borderColor: '#00ffeb', backgroundColor: '#1facbb' }],
        },
      },
      {
        title: 'Gordura Corporal',
        ref: ref(null),
        id: 'gorduraCorporal',
        color: 'red',
        data: {
          labels: state.chart.gorduraCorporal.map((v, i) => `Exame ${i}`),
          datasets: [{ label: '%', data: state.chart.gorduraCorporal, borderColor: '#00ffeb', backgroundColor: '#1facbb' }],
        },
      },
      {
        title: 'Gordura Visceral',
        ref: ref(null),
        id: 'gorduraVisceral',
        color: 'red',
        data: {
          labels: state.chart.gorduraVisceral.map((v, i) => `Exame ${i}`),
          datasets: [{ label: '%', data: state.chart.gorduraVisceral, borderColor: '#00ffeb', backgroundColor: '#1facbb' }],
        },
      },
      {
        title: 'Água Corporal',
        ref: ref(null),
        id: 'aguaCorporal',
        color: 'red',
        data: {
          labels: state.chart.aguaCorporal.map((v, i) => `Exame ${i}`),
          datasets: [{ label: '%', data: state.chart.aguaCorporal, borderColor: '#00ffeb', backgroundColor: '#1facbb' }],
        },
      },
      {
        title: 'IMC',
        ref: ref(null),
        id: 'imc',
        color: 'red',
        data: {
          labels: state.chart.imc.map((v, i) => `Exame ${i}`),
          datasets: [{ label: '%', data: state.chart.imc, borderColor: '#00ffeb', backgroundColor: '#1facbb' }],
        },
      },
    ]

    onMounted(() => fetchData())

    function parseDate(date) {
      return Intl.DateTimeFormat('pt-BR').format(date)
    }

    return {
      state,
      chartData,
      baseOptions,
      format,
      update,
      toggleActiveExam,
    }
  },
}
</script>
