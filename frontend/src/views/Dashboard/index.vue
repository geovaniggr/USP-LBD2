<template>
  <div class="container-wrapper">
    <section class="flex py-16 justify-between items-center">
      <div class="flex gap-8 pr-8 border-r border-white">
        <div class="flex flex-col bg-brand-box text-white items-center p-8 rounded-md">
          <span class="py-1 px-6 bg-red-600 text-white uppercase font-bold text-sm mb-4">Plano {{ state.user.tipoPlano }}</span>
          <img src="../../assets/male-mock.jpeg" alt="" class="block rounded-full w-32 h-32 object-contain" style="object-fit: cover" />
          <h3 class="text-lg font-bold text-white mt-4">{{ state.user.nome }}</h3>
        </div>
        <div class="text-white relative t-8">
          <button class="border-0 absolute top-0 right-0 bg-brand-primary p-1 rounded-md text-black font-bold hover:opacity-80" @click="toggleUpdateProfile()">Alterar</button>
          <label>
            Nome de Usuário:
            <input class="bg-transparent block border border-brand-primary pt-2 px-1 mb-2 rounded-sm" v-model="state.user.nome" :disabled="!shouldEnableUpdateProfile" />
          </label>
          <label class="pt-2">
            Email:
            <input class="bg-transparent block border border-brand-primary px-1 my-2 rounded-sm" v-model="state.user.email" :disabled="!shouldEnableUpdateProfile" />
          </label>
          <label class="pt-2">
            Objetivo:
            <select class="bg-transparent block border border-brand-primary p-2 text-white" v-model="state.user.objetivo">
              <option :value="HIPERTROFIA">Hipertrofia</option>
              <option :value="EMAGRECIMENTO">Emagrecimento</option>
              <option :value="RESISTENCIA">Resistência</option>
              <option :value="SAUDE">Saúde</option>
            </select>
          </label>
          <label>
            Fim do Plano:
            <p class="mt-4 border border-brand-primary">
              {{ format(state.user.fimDoPlano) }}
            </p>
          </label>
        </div>
      </div>
      <div class="w-[700px]">
        <div class="w-3/4 p-8">
          <h3 class="text-white font-bold uppercase text-2xl mb-4">Contrate um Personal</h3>
          <div>
            <span class="block bg-white text-center font-bold rounded-md py-1">Sexta, 13 de Junho</span>
            <select class="bg-transparent text-white text-lg border border-white rounded-md my-4 p-2" :value="state.active.name">
              <option v-for="(teacher, index) of state.teacher" :key="index" :value="teacher.name" @click="changeActive(teacher)">
                {{ teacher.name }}
              </option>
            </select>
            <div class="flex flex-wrap gap-4 work">
              <div v-for="(hour, i) of state.active.hours" :key="i" class="text-white relative border border-white rounded-md mr-2" :class="{ checked: state.selectedHours.includes(hour) }">
                <input type="checkbox" :value="hour" class="opacity-0 cursor-pointer h-8 w-24 p-4 block" :id="hour" @click="() => addHour(hour)" />
                <label :for="hour" class="absolute cursor-pointer top-1/2 left-1/2 transform -translate-y-1/2 -translate-x-1/2">
                  {{ hour }}
                </label>
              </div>
            </div>
            <button class="mt-4 p-2 rounded-md block w-full bg-brand-primary uppercase font-bold hover:opacity-80">Agendar Agora</button>
          </div>
        </div>
      </div>
    </section>
    <section class="mb-20">
      <h3 class="text-2xl text-white text-center uppercase">Você pode precisar</h3>
      <p class="text-center text-brand-primary">Conheça os benefícios do seu plano</p>
      <ul class="grid grid-cols-2 gap-8 mt-8">
        <li class="flex flex-col justify-around bg-brand-box h-64 py-4 px-10 rounded-md">
          <span>X </span>
          <h5 class="text-white uppercase text-lg font-bold">Visualize seus treinos</h5>
          <p class="text-gray-200 text-sm w-4/5">Monte seu treino com os instrutores e tenha acesso em qualquer lugar, com direito a vídeo de execução</p>
          <button class="border uppercase w-1/2 border-brand-primary rounded-md p-2 text-brand-primary hover:bg-brand-primary hover:text-black" @click="() => router.push('Exercises')">
            Ver meus treinos
          </button>
        </li>
        <li class="flex flex-col justify-around bg-brand-box h-64 p-4 rounded-md">
          <span>X </span>
          <h5 class="text-white uppercase text-lg font-bold">Acompanhe sua evolução</h5>
          <p class="text-gray-200 text-sm">Realize os exames de biompêndancia e veja o seu desempenho, melhore seus resultados.</p>
          <button class="border uppercase w-1/2 border-brand-primary rounded-md p-2 text-brand-primary hover:bg-brand-primary hover:text-black" @click="() => router.push('Exams')">
            Ver meus exames
          </button>
        </li>
      </ul>
    </section>
  </div>
</template>
<script>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const router = useRouter()
    const shouldEnableUpdateProfile = ref(false)

    function format(date) {
      return new Intl.DateTimeFormat('pt-BR').format(new Date(date))
    }

    const state = reactive({
      user: {
        name: 'Paula Nobre',
        email: 'paula.nobre@gmail.com',
        objetivo: 'HIPERTROFIA',
        tipoPlano: 'BASICO',
        fimDoPlano: new Date('2022-06-06').toDateString('pt-BR'),
      },
      active: {
        name: 'José Augusto',
        hours: ['07:00', '08:00', '09:00', '10:30', '15:00'],
      },
      selectedHours: [],
      teacher: [
        {
          name: 'José Augusto',
          hours: ['07:00', '08:00', '09:00', '10:30', '15:00'],
        },
        { name: 'Mirella Silva', hours: ['17:00', '18:00', '19:00', '22:00'] },
        { name: 'Paulo', hours: ['10:00', '11:00', '12:00', '13:30', '18:00'] },
        {
          name: 'Leticia Ribeiro',
          hours: ['07:30', '08:30', '09:30', '11:30', '16:00'],
        },
      ],
    })

    function getHoursAsString(date) {
      const d = new Date(date)
      return `${String(d.getHours()).padStart(2, 0)}:${String(d.getMinutes()).padStart(2, 0)}`
    }

    function filterAvaibleHours(filleds) {
      const avaibles = ['07:00', '08:00', '09:00', '10:30', '15:00', '16:00', '18:00', '19:00', '20:00', '21:00', '22:00']
      return avaibles.filter((hour) => !filleds.includes(hour))
    }

    onMounted(() => {
      fetch('http://localhost:8080/api/dashboard/4')
        .then((r) => r.json())
        .then(({ client, instructors }) => {
          const parsedInstructors = Object.values(instructors).map((instructor) => ({
            name: instructor.nome,
            hours: filterAvaibleHours(instructor.horarios.map(getHoursAsString)),
          }))

          state.user = { id: client.id, nome: client.nome, email: client.email, fimDoPlano: client.finalMatricula, objetivo: 'HIPERTROFIA', tipoPlano: 'BASICO' }
          state.teacher = parsedInstructors
        })
    })

    function addHour(hour) {
      const hourIndex = state.selectedHours.findIndex((h) => h == hour)

      if (hourIndex >= 0) {
        state.selectedHours.splice(hourIndex, 1)
      } else {
        state.selectedHours.push(hour)
      }
    }

    function changeActive(teacher) {
      console.log(teacher)
      state.active = teacher
      state.selectedHours = []
    }

    function toggleUpdateProfile() {
      shouldEnableUpdateProfile.value = !shouldEnableUpdateProfile.value
    }

    return {
      state,
      shouldEnableUpdateProfile,
      changeActive,
      toggleUpdateProfile,
      addHour,
      router,
      format,
    }
  },
}
</script>
<style scoped>
.checked {
  background-color: white;
  color: black;
}
</style>
