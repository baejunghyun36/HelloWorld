<template>
    <div class="wrapper">
        <div>
            <div class="wrap">
                <div id="username">{{ this.nickname }} 님의 미니홈피</div>
                <div v-if="this.isFamily==3 && this.masterSeq!=this.userSeq" class="follow-request-btn" id="show-modal" @click="showModal = true">
                    일촌 신청
                </div>
                <div v-if="this.isFamily==1 && this.masterSeq!=this.userSeq" class="follow-reply-btn" id="show-modal" @click="showModal2 = true">
                    일촌 수락
                </div>
                <div v-if="this.isFamily==2 && this.masterSeq!=this.userSeq" class="follow-reply-btn" id="show-modal" @click="deleteFamily">
                    일촌 삭제
                </div>
                <!-- <div class="bgm-btn">
                    ♬ {{this.songName}} - {{this.singerName}}
                </div> -->
            </div>
            <Teleport to="body">
                <modal :show="showModal" @close="showModal = false">
                    <template #header>
                        <h3>custom header</h3>
                    </template>
                </modal>
            </Teleport>
            <Teleport to="body">
                <Modal2 :show="showModal2" @close="showModal2 = false">
                    <template #header>
                        <h3>custom header</h3>
                    </template>
                </Modal2>
            </Teleport>
        </div>
        <BGMComp />
    </div>
</template>


<script>
import Modal from '@/components/FollowComp/FollowRequestModal.vue'
import Modal2 from '@/components/FollowComp/FollowReplyModal.vue'
import BGMComp from '@/components/BasicComp/BGMComp.vue'
import http from '@/api/httpWithAccessToken'

export default {
    components: { Modal, Modal2, BGMComp },
    data() {
        return {
            showModal: false,
            showModal2: false,
            masterSeq: this.$route.params.userSeq,
            userSeq: localStorage.getItem('user-seq'),
            nickname: null,
            isFamily: null,
            songName: null,
            singerName: null,
        }
    },
    mounted() {
        http.get(`/user/mainpage/${this.masterSeq}`).then((result) => {
            this.nickname = result.data.data.nickname;
            this.isFamily = result.data.data.isFamily;
            // console.log(result.data.data)
        }, (error) => {
            console.log(error);
        });
        http.get(`/user/mainpage/${this.userSeq}`).then((result) => {
            this.songName = result.data.data.bgmList[0].title;
            this.singerName = result.data.data.bgmList[0].artist;
        }, (error) => {
            console.log(error)
        })
    },
    methods: {
        deleteFamily: function() {
            http.delete(`/family?fromUserSeq=${this.userSeq}&toUserSeq=${this.masterSeq}`).then((result) => {
                console.log(result)
                window.location.reload();
            }, (error) => {
                console.log(error)
            })
        }
    }
}
</script>

<style scoped>
.wrapper {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    /* margin-right : 5rem; */
    align-items: end;
}

a {
    text-decoration: none;
    color: #499DC6;
}

a:hover {
    text-decoration: underline;
}

.wrap {
    display: flex;
}

#username {
    padding-left: 1vw;
    font-size: 15px;
    font-weight: bold;
    /* line-height: 25px; */
    color: #499DC6;
}

.follow-request-btn {
    cursor: pointer;
    font-size: 10px;
    background-color: #82ACC1;
    color: #FFFFFF;
    width: 60px;
    /* height: 20px; */
    padding: 2px 4px;
    text-align: center;
    /* zoom: 0.8; */
    font-weight: bold;
    border-radius: 20px;
    margin-left: 15px;
    /* line-height: 20px; */
    /* margin-top: 6px; */
    margin-bottom: 5px;
}

.follow-reply-btn {
    cursor: pointer;
    font-size: 10px;
    background-color: #82ACC1;
    color: #FFFFFF;
    width: 60px;
    /* height: 20px; */
    padding: 2px 4px;
    text-align: center;
    /* zoom: 0.8; */
    font-weight: bold;
    border-radius: 20px;
    margin-left: 15px;
    /* line-height: 20px; */
    /* margin-top: 6px; */
    margin-bottom: 5px;
}

.bgm-btn {
    cursor: pointer;
    font-size: 12px;
    padding: 2px 0px;
    text-align: center;
    font-weight: bold;
    border-radius: 20px;
    /* line-height: 20px; */
    /* margin-top: 6px; */
    margin-left: 20px;
    margin-bottom: 5px;
}
</style>