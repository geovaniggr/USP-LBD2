<template>
  <router-view />
</template>

<style lang="postcss">
html,
body,
#app {
  height: 100%;
  @apply bg-brand-black;
}

body {
  font-family: "Work Sans", sans-serif;
}

.container-wrapper {
  max-width: 1200px;
  margin-left: auto;
  margin-right: auto;
}

#app {
  @apply bg-brand-black;
}
</style>
