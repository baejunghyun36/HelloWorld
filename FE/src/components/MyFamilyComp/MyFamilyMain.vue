<template>
    <div class="wrap">
        <!-- <div class="blank"></div> -->
        <UserTitleComp />
        <div class="right-body">
            HIHI
        </div>
    </div>
</template>

<script>
import UserTitleComp from "@/components/BasicComp/UserTitleComp.vue"

export default {
    components: { UserTitleComp, },
    methods: {

    },
    created() {
       
    },
    data() {
        return {

        }
    },
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
</style>
