<template>
    <div id="CategoryList">
        <div id="title">게시글</div>
        <hr>
        <div id="category">
            <div>
                <img src="../../assets/folder.png" alt="">
                <p>CS</p>
            </div>
            <div>
                <img src="../../assets/folder.png" alt="">
                <p>Algorithm</p>
            </div>
            <div>
                <img src="../../assets/folder.png" alt="">
                <p>Project</p>
            </div>
        </div>
    </div>
</template>

<script>
</script>

<style scoped>
#CategoryList {
    width : 20vw;
    height: 78vh;
    padding-left : 3vh;
    padding-top : 3vh;
}

hr {
    width : 25vh;
    text-align: left;
    margin-left : 0;
}

#title {
    font-size: 1.6vw;
    font-weight: bold;
    color:#82ACC1;
    display: flex;
    padding-left : 1.5vh;
    padding-bottom : 1.5vh;
}

#category {
    font-size: 1vw;
    color: black;
    margin: 1vh;
}

#category > div {
    display: flex;
}

img {
    height:3vh;
    width: 3vh;
    padding : 1.5vh 1.5vh 0 0;
}
</style>