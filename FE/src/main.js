import { createApp } from 'vue';
import App from './App.vue';
import {router} from './router/index.js';
import VueSplide from '@splidejs/vue-splide';
import VueApexCharts from "vue3-apexcharts";

//createApp(App).mount('#app')

const app = createApp(App);
app.use(router)
app.use(VueSplide)
app.use(VueApexCharts)
// app.component('apexChart', VueApexChart)
app.mount("#app")