<template>
    <div class="family-comment-title">일촌평</div>
    <div class="comments">
        <div class="comment" v-for="comment in this.familyComment" :key="comment.id">
            <div class="comment-author" @click="mvOther" :id="comment.userSeq">
                {{ comment.familyNickName }} ({{ comment.relationName }})
            </div>
            <div class="comment-content">
                {{ comment.relationComment }}
            </div>
        </div>
    </div>
    <div class="comment-input-container">
        <div class="author-name">{{ this.nickname }} (나)</div>
        <input class="comment-input" placeholder="자기 자신은 일촌평을 등록할 수 없어요" v-model="postMsg" v-if="this.userSeq==this.masterSeq" disabled/>
        <button class="comment-post-btn" disabled v-if="this.userSeq==this.masterSeq">
            등록
        </button>
        <input class="comment-input" placeholder="일촌평을 등록해보세요" v-model="postMsg" v-if="isFamily==2 && this.userSeq!=this.masterSeq"/>
        <button class="comment-post-btn" @click="postFamilyComment" v-if="isFamily==2 && this.userSeq!=this.masterSeq">
            등록
        </button>
        <input class="comment-input" placeholder="우선, 일촌을 맺어주세요" v-model="postMsg" disabled v-if="isFamily!=2 && this.userSeq!=this.masterSeq"/>
        <button class="comment-post-btn" disabled v-if="isFamily!=2 && this.userSeq!=this.masterSeq">
            등록
        </button>
    </div>
</template>

<script>
import http from '@/api/httpWithAccessToken'
export default {
    data() {
        return {
            masterSeq: this.$route.params.userSeq,
            userSeq: localStorage.getItem('user-seq'),
            nickname: null,
            postMsg: null,
            familyComment: [],
            isFamily: null,
        }
    },
    mounted() {
        http.get(`/user/mainpage/${this.masterSeq}`).then((result) => {
            // console.log(result.data.data);
            console.log(result.data.data)
            this.familyComment = result.data.data.familyResponseDtos;
            this.isFamily = result.data.data.isFamily
        }, (error) => {
            console.log(error);
        });
        http.get(`/user/userInfo/${this.userSeq}`).then((result) => {
            // console.log(result.data.data);
            this.nickname = result.data.data.nickname
        }, (error) => {
            console.log(error);
        });
    },
    methods: {
        postFamilyComment: function () {
            var info = {
                comment: this.postMsg,
                fromUserSeq: this.userSeq,
                toUserSeq: this.masterSeq,
            };
            console.log(info);
            http.put(`/family/comment`, JSON.stringify(info)).then((result) => {
                console.log(result);
                window.location.reload()
            }, (error) => {
                console.log(error);
            });
            this.familyComment.push(info);
        },
        mvOther: function(e) {
            e.preventDefault();
            console.log(e.target.id);
            var link = document.location.href;
            if (link.includes('localhost')) {
                window.location.replace(`http://localhost:8081/mainpage/${e.target.id}`);
            }
            else {
                window.location.replace(`https://k8a308.p.ssafy.io/mainpage/${e.target.id}`);
            }
        }
    }
}
</script>

<style>
.comments {
    height: 60%;
    overflow-y: scroll;
}

.comments::-webkit-scrollbar {
    width: 8px;
    /* 스크롤바의 너비 */
}

.comments::-webkit-scrollbar-thumb {
    height: 20%;
    /* 스크롤바의 길이 */
    background: #6A6A6A;
    /* 스크롤바의 색상 */

    border-radius: 10px;
}

.comments::-webkit-scrollbar-track {
    background: #D9D9D9;
    /*스크롤바 뒷 배경 색상*/
}

.family-comment-title {
    text-align: start;
    color: #6A6A6A;
    font-size: 13px;
    margin-left: 5px;
    margin-top: 3px;
    font-weight: bold;
    /* zoom: 0.9; */
}

.comment {
    margin: 0 auto;
    margin-top: 2px;
    width: 100%;
    text-align: start;
    display: flex;
    font-size: 12px;
}

.comment-author {
    width: 16%;
    color: #82ACC1;
    margin-left: 5px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    /* zoom: 0.8; */
    font-weight: bold;
    cursor: pointer;
}

.comment-author:hover {
    text-decoration: underline;
    text-decoration-color: #82ACC1;
}

.comment-content {
    width: 65%;
    color: #6A6A6A;
    margin-left: 7px;
    /* zoom: 0.8; */
}

.comment-created-time {
    color: #6A6A6A;
    margin-left: 10px;
    /* zoom: 0.8; */
}

.comment-input-container {
    display: flex;
    font-size: 12px;
    margin-top: 3px;
    margin-bottom: 5px;
    width: 100%;
}

.comment-input::placeholder {
    color: #A5A5A5;
}

.author-name {
    width: 11%;
    color: #82ACC1;
    /* margin-left: 5px; */
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    /* zoom: 0.8; */
    font-weight: bold;
    text-align: end;
    margin-right: 5px;
}

.comment-input {
    font-size: 12px;
    /* zoom: 0.7; */
    width: 68%;
    box-sizing: border-box;
    border: 1px solid #9A9A9A;
    background-color: #F5F5F5;
    box-shadow: 0.2px 0.4px #8C8C8C;
    border-radius: 3px;
    padding: 3px;
}

.comment-post-btn {
    margin-left: 10px;
    background-color: #F9F9F9;
    color: #6A6A6A;
    font-size: 10px;
    padding: 1px 6px;
    /* zoom: 0.8; */
    border-radius: 3px;
    font-weight: 600;
    box-shadow: 1px 0.8px #8C8C8C;
    cursor: pointer;
}
</style>