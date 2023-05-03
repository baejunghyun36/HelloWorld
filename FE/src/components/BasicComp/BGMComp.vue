<template>
    <div class="BN">
        <img src="@/assets/icon/alarm.png" alt="" class="notification" @click="showModal = true" style="cursor:pointer">
        <Teleport to="body">
            <modal :show="showModal" @close="showModal = false"/>
        </Teleport>
    </div>
    
</template>

<script>
import Modal from '@/components/NotifyComp/NotificationComp.vue';
import http from '@/api/httpWithAccessToken';
export default {
    components: {Modal},
    data() {
        return {
            showModal : false,
        }
    },
    methods: {
        logout: function () {
            var temp = localStorage.getItem('access-token');
            var tokens = {
                accessToken: temp,
            }
            console.log(tokens);
            http.post(`/user/logout`, JSON.stringify(tokens)).then(
                (response) => {
                    console.log(response);
                    localStorage.removeItem("access-token");
                    localStorage.removeItem("user-seq");
                    localStorage.removeItem("refresh-token");
                    // localStorage.clear();
                    window.location.replace('https://k8a308.p.ssafy.io/');
                    // this.$router.push({ name: 'before-login' });
                },
                (error) => {
                    console.log(error);
                    alert("로그아웃 실패!");
                }
            )
        }
        // play(sound) {
        //     if (sound) {
        //         var audio = new Audio(sound);
        //         audio.play();
        //     }
        // }
    }
}
</script>

<style scoped>
    .notification {
        width : 1vw;
    }
</style>