<template>
  <header class="w-full bg-black h-16">
    <nav class="h-full container-wrapper flex items-center justify-around pr-8">
      <h1 class="text-brand-primary font-bold uppercase text-2xl font-mono">Uspfit</h1>
      <ul class="flex">
        <li class="cursor-pointer text-brand-primary mx-2" @click="to('Exercises')">Treinos</li>
        <li class="cursor-pointer text-brand-primary mx-2" @click="to('Exams')">Biomimpendância</li>
        <li class="cursor-pointer text-brand-primary mx-2" @click="to('CreateExercise')">Criar Treinos</li>
        <li class="cursor-pointer text-brand-primary mx-2" @click="to('CreateExam')">Criar Bioimpendância</li>
      </ul>
      <img class="w-10 mx-4" src="../../assets/another-logo.svg" alt="Logo da Uspfit" />
    </nav>
  </header>
</template>
<script>
import { useRouter } from 'vue-router'

export default {
  setup() {
    const router = useRouter()

    function to(name) {
      router.push({ name })
    }
    return {
      router,
      to,
    }
  },
}
</script>
