<template>
  <Header />
  <div class="container-wrapper w-full">
    <h1 class="text-brand-primary text-3xl font-bold mt-8 uppercase text-center">Buscar Aluno</h1>
    <input list="clients" class="block rounded px-8 py-4 w-1/2 mt-8 mx-auto" :value="state.selected" @change="execute" />
    <datalist id="clients">
      <option v-for="(usr, index) of client" :key="index" :value="usr.email">{{ usr.nome }} -- {{ usr.email }}</option>
    </datalist>
    <section>
      <div class="mt-8">
        <header class="rounded bg-brand-primary w-full h-16 px-8 flex items-center justify-between">
          <h3 class="text-3xl uppercase font-bold">Dados do Exame</h3>
        </header>
        <form class="mt-8 flex flex-wrap items-center justify-center gap-4">
          <div class="bg-black text-white p-8 rounded">
            <label class="text-lg block text-center mb-auto">IMC</label>
            <input v-model="state.exame.imc" class="w-2/4 block mx-auto mt-4 p-2 rounded text-black" type="number" />
          </div>
          <div class="bg-black text-white p-8 rounded">
            <label class="text-lg block text-center">Massa Magra</label>
            <input v-model="state.exame.massaMagra" class="w-2/4 block mx-auto mt-4 p-2 rounded text-black" type="number" />
          </div>
          <div class="bg-black text-white p-8 rounded">
            <label class="text-lg block text-center">Água Corporal</label>
            <input v-model="state.exame.aguaCorporal" class="w-2/4 block mx-auto mt-4 p-2 rounded text-black" type="number" />
          </div>
          <div class="bg-black text-white p-8 rounded">
            <label class="text-lg block text-center">Gordura Corporal</label>
            <input v-model="state.exame.gorduraCorporal" class="w-2/4 block mx-auto mt-4 p-2 rounded text-black" type="number" />
          </div>
          <div class="bg-black text-white p-8 rounded">
            <label class="text-lg block text-center">Gordura Visceral</label>
            <input v-model="state.exame.gorduraVisceral" class="w-2/4 block mx-auto mt-4 p-2 rounded text-black" type="number" />
          </div>
        </form>
        <button type="submit" class="py-8 px-12 rounded rounded-md bg-brand-primary uppercase text-2xl text-bold">Finalizar</button>
      </div>
    </section>
  </div>
</template>
<script>
import Header from '@/components/Header'
import { reactive } from 'vue'

export default {
  components: {
    Header,
  },
  setup() {
    const state = reactive({
      selected: '',
      exame: {
        imc: 10,
        massaMagra: 0,
        aguaCorporal: 0,
        gorduraCorporal: 0,
        gorduraVisceral: 0,
      },
    })

    function execute(event) {
      console.log(event.target.value)
    }

    const client = [
      { nome: 'Geovani Granieri', email: 'geovani.granieri@usp.br' },
      { nome: 'Mc Hariel', email: 'hariel@mc.br' },
      { nome: 'Richard Rasmussen', email: 'richard@bio.br' },
    ]

    function log() {
      console.log(state.selected)
    }

    return {
      state,
      client,
      log,
      execute,
    }
  },
}
</script>
