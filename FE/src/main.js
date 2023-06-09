import { createApp } from "vue";
import App from "./App.vue";
import { router } from "./router/index.js";
import VueSplide from "@splidejs/vue-splide";
import VueApexCharts from "vue3-apexcharts";
import { plugin as VueTippy } from "vue-tippy";
import "tippy.js/dist/tippy.css";
import timeago from 'vue-timeago3';
import { ko } from 'date-fns/locale';
import Tabs from 'vue3-tabs';
import VueYtframe from "vue3-ytframe";
import VueSSE from 'vue-sse';
import InfintiedLoading from "v3-infinite-loading";
import '@imengyu/vue3-context-menu/lib/vue3-context-menu.css'
import ContextMenu from '@imengyu/vue3-context-menu'
import PerfectScrollbar from 'vue3-perfect-scrollbar'
import 'vue3-perfect-scrollbar/dist/vue3-perfect-scrollbar.css'
//createApp(App).mount('#app')

const app = createApp(App);
//app.use(store);
app.use(router);
app.use(VueSplide);
app.use(VueApexCharts);
app.use(VueYtframe);
app.use(VueSSE);
app.use(PerfectScrollbar, {
  watchOptions : true,
  options : {
    suppressScrollX : true
  }
});
app.use(ContextMenu);
const timeagoOptions = {
    converterOptions: {
        includeSeconds : true,
    },
    locale: ko,
}

app.use(
  VueTippy,
  // optional
  {
    directive: "tippy", // => v-tippy
    component: "tippy", // => <tippy/>
    componentSingleton: "tippy-singleton", // => <tippy-singleton/>,
    defaultProps: {
      placement: "auto-end",
      allowHTML: true,
    }, // => Global default options * see all props
  }
);
app.use(Tabs);
// app.component('apexChart', VueApexChart)

app.use(timeago, timeagoOptions);
app.component("infinite-loading", InfintiedLoading);
app.mount("#app")
