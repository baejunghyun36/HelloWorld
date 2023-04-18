<template>
    <div>
        <p style="font-size:0.7rem">TODAY</p>
        <p id="today">1234</p>
        <p style="font-size:0.7rem; padding-right : 0.5vw"> | </p>
        <p style="font-size:0.7rem">TOTAL</p>
        <p id="total">123123</p>
    </div>
</template>

<script>
</script>

<style scoped>
    div{
        display : flex;
        justify-content: center;
        align-items: center;
    }

    #today {
        font-size : 0.8rem;
        color : red;
        font-weight: bold;
        padding: 0 0.5vw 0 0.5vw;
    }
    #total {
        font-size: 0.8rem;
        font-weight: bold;
        padding: 0 0.5vw 0 0.5vw;
    }
</style>