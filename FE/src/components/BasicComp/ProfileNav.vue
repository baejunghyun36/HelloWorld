<template>
    <div id = "leftFrame">
        <BoardCategoryList/>
    </div>
</template>


<script>
import BoardCategoryList from '@/components/BoardComp/BoardCategoryList.vue'
export default {
    components : {BoardCategoryList},
};
</script>

<style scoped>
#leftFrame {
    width: 18vw;
    height: 78vh;
    border-style : solid;
    border-radius : 2vh;
    border-color: black;
    background-color: white;
    padding : 2vh;
}

</style>