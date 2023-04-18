<template>
    <div id = "main">
        <BoardListComp/>
    </div>
</template>

<script>
import BoardListComp from '@/components/BoardComp/BoardListComp.vue'
export default {
    components : {BoardListComp},
};
</script>

<style scoped>
    #main {
        height: 78vh;
        width : 60vw;
        border-style : solid;
        border-radius : 2vh;
        border-color: black;
        background-color: white;
        padding : 2vh;
        margin-left : 2vh;
    }
    .BoardListComp {
        margin : 2vh 2vw 2vh 2vw;
    }
</style>