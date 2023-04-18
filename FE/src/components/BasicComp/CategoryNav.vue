<template>
    <nav id = "category">
        <ul>
            <li class="navitem">
                <router-link to="/" class="nav-link">홈</router-link>
            </li>
            <li class="navitem">
                <router-link to="/boardlist" class="nav-link">게시글</router-link>
            </li>
            <li class="navitem">
                <router-link to="/guestbook" class="nav-link">방명록</router-link>
            </li>
            <li class="navitem">
                <router-link to="/user/notify" class="nav-link">알림</router-link>
            </li>
        </ul>
    </nav>
</template>


<script>
</script>

<style scoped>
    #category {
        height: 78vh;
    }

    ul {
        list-style-type: none;
        padding : 0;
    }
    #category .navitem .nav-link {
        text-decoration: none;
        height : 6vh;
        width : 4.5vw;
        border-style: solid;
        border-radius : 0 1vw 1vw 0;
        margin-bottom : 1.5vh;
        background-color: #499DC6;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #category .navitem .nav-link .router-link {
        color : white;
    } 
    .navitem .nav-link a.router-link-exact-active {
        height : 6vh;
        width : 4.5vw;
        border-style: solid;
        border-radius : 0 1vw 1vw 0;
        margin-bottom : 1.5vh;
        background-color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        color : black !important;
    }
</style>