<template>
    <div class="wrap">
        <!-- <div class="blank"></div> -->
        <UserTitleComp />
        <div class="right-body">
            <div class="community-notice"># 다양한 게시글들을 만나보세요</div>
            <tabs v-model="selectedTab">
                <tab class="tab" v-for="(tab, i) in tabs" :key="`t${i}`" :val="tab" :label="tab" :indicator="true" />
            </tabs>
            <tab-panels v-model="selectedTab" :animate="true">
                <tab-panel v-for="(tab, i) in tabs" :key="`tp${i}`" :val="tab">
                    {{ tab }}
                </tab-panel>
            </tab-panels>
        </div>
    </div>
</template>

<script>
import UserTitleComp from "@/components/BasicComp/UserTitleComp.vue"
import { Tabs, Tab, TabPanels, TabPanel } from 'vue3-tabs';
import {reactive, toRefs } from 'vue';

const tabs = ['A', 'B', 'C'];


export default {
    components: { UserTitleComp, Tabs, Tab, TabPanels, TabPanel },
    setup() {
    const state = reactive({
      selectedTab: tabs[1]
    });
    return {
      tabs,
      ...toRefs(state)
    };
  }
}

</script>

<style scoped>
.right-body {
    background-color: white;
    width: 62vw;
    height: 75vh;
    border: 1px solid #6A6A6A;
    border-radius: 15px;
}

.community-notice {
    text-align: start;
    color: #6A6A6A;
    font-size: 20px;
    /* font-weight: 600; */
    margin-left: 3%;
    margin-top: 2%;
    margin-bottom: 3%;
}
</style>
