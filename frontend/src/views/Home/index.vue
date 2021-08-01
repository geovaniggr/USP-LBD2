<template>
  <main>
    <header class="w-full bg-black h-16">
      <nav class="h-full container-wrap flex items-center justify-end pr-8">
        <h1 class="text-brand-primary font-bold uppercase text-2xl font-mono">
          Uspfit
        </h1>
        <img
          class="w-10 mx-4"
          src="../../assets/another-logo.svg"
          alt="Logo da Uspfit"
        />
      </nav>
    </header>
    <div class="container-wrapper flex flex-col p-16 w-full">
      <div class="w-2/4">
        <h1 class="text-brand-primary text-6xl font-bold uppercase">
          Começe <br />uma nova<br />
          rotina
        </h1>
        <p class="text-white text-xl my-4">
          De seus primeiros passos para <br />melhorar sua saúde e seu físico
        </p>
      </div>
      <div class="mt-4">
        <a
          @click="() => router.push({ name: 'Dashboard' })"
          class="
            inline-block
            text-black text-xl text-bold
            uppercase
            px-8
            py-4
            bg-brand-primary
            rounded-sm
            cursor-pointer
            transition
            transform
            hover:-translate-y-2
          "
        >
          Entrar
        </a>
      </div>
    </div>
  </main>
</template>
<script>
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();

    return {
      router,
    };
  },
};
</script>
<style scoped>
main {
  position: relative;
  height: 95vh;
  min-height: 600px;
  width: 100%;
  z-index: 5;
}

main::before {
  content: "";
  display: block;
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  background-image: url("../../assets/home.jpg");
  background-size: cover;
  background-blend-mode: soft-light;
  filter: brightness(0.45);
  z-index: -1;
}
</style>
