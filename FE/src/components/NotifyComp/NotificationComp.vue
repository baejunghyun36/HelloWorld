<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
          <div class="modal-container">
            <div class="modal-header" @click="$emit('close')">
                <img src="@/assets/icon/close.png" alt="" style="cursor:pointer">
            </div>
            <ul class="notification-list">
                <!-- <li v-if="notifications.length === 0" class="notification-none">
                    <span>알림창이 비었습니다!</span>
                </li> -->
               <li v-for="notification in notifications" :key="notification?.notifySeq" class="notification"
                :style="{ backgroundColor: notification.readState ? 'white' : '#FAF3E8'}">
                    <div>
                        {{ notification.title }}
                    </div>
                        <!-- <div v-if="notification?.type === 0" class="typeZero" @click="putNotification(notification?.notifySeq)">
                            <span>{{ notification?.title }}</span>
                            <span class="date"> <timeago :datetime="notification.createdTime" auto-update /></span>
                        </div>
                        <div v-else-if="notification?.type === 1" class="typeOne" @click="putNotification(notification?.notifySeq)">
                            <span>{{ notification?.title }}</span>
                            <span class="date"> <timeago :datetime="notification.createdTime" auto-update /></span>
                        </div>
                        <div v-else-if="notification?.type === 2" class="typeTwo" @click="putNotification(notification?.notifySeq)">
                            <span>{{ notification?.title }}</span>
                            <span class="date"> <timeago :datetime="notification.createdTime" auto-update /></span>
                        </div>
                        <div v-else-if="notification?.type === 3" class="typeThree" @click="putNotification(notification?.notifySeq)">
                            <span>{{ notificaiton?.title }}</span>
                            <span class="date"> <timeago :datetime="notification.createdTime" auto-update /></span>
                        </div>
                        <div v-else-if="notification?.type === 4" class="typeFour" @click="putNotification(notification?.notifySeq)">
                            <span>{{ notification?.title }}</span>
                            <span class="date"> <timeago :datetime="notification.createdTime" auto-update /></span>
                        </div> -->
                    <img src="@/assets/icon/trash.png" alt="" @click="deleteNotification(notification)" style="cursor:pointer">
                </li>
            </ul>
          </div>
        </div>
    </Transition>
</template>

<script>
//import { ref, watchEffect } from 'vue';
//import axios from 'axios';

export default {
    props: {
      show: Boolean,
    },
    // setup() {
    //     const notifications = ref([]);

    //     const subscribeToSSE = () => {
    //         const url = `/notify/7`;
    //         const headers = {
    //             Authorization : `Bearer ${localStorage.getItem('access-token')}`,
    //             Connection : 'keep-alive',
    //             Accept: 'text/event-stream',
    //             'Cache-Control': 'no-cache',
    //         };

    //         const eventSource = new EventSource(url, { headers});
            
    //         eventSource.onmessage = (event) => {
    //             const eventData = JSON.parse(event.data)
    //             console.log('eveententetnetn', eventData);
    //             notifications.value = event.data;
    //         };
    //     };
        
    //     const fetchNoticeData = () => {
    //         getNotice();
    //     };

    //     const getNotice = () => {
    //         const headers = {
    //             "Content-Type": "application/json;charset=utf-8",
    //              Authorization: `Bearer ${localStorage.getItem("access-token")}`,
    //         };
    //         axios.get(`/notify/7`, {headers})
    //         .then(response => {
    //             console.log(response.data.data);
    //             notifications.value = response;
    //         })
    //         .catch(error => {
    //             console.error(error);
    //         })
    //     };

    //     watchEffect(() => {
    //         subscribeToSSE();
    //         fetchNoticeData();
    //     })
    // }
}


//import axios from 'axios';
//import VueSSE from 'vue-sse';

// export default {
//     props: {
//         show: Boolean
//     },
//     data() {
//         return {
//             notifications :[]
//         };
//     },
//     mounted() {
//         this.subscribeToSEE();
//         this.loadNotifyList();
//     },
//     methods: {
//         subscribeToSEE() {
//             const headers = {
//                 "Authorization" : `Bearer ${localStorage.getItem('access-token')}`,
//                 "Connection" : "keep-alive",
//                 "Cache-Control" : "no-cache",
//             }
//             const eventSource = new EventSource(`notify/7`, {headers});

//             eventSource.addEventListener('message', event => {
//                 console.log("asdfasdf");
//                 console.log(event.data);
//                 const notification = JSON.parse(event.data);
//                 this.notifications.push(notification);
//             });

//             eventSource.addEventListener('error', error => {
//                 console.error('SSE connection error', error);
//             })
//         },
//         loadNotifyList() {
//             const headers = {
//                 "Content-Type": "application/json;charset=utf-8",
//                 Authorization: `Bearer ${localStorage.getItem("access-token")}`,
//             }
//             axios.get(`notify/7`, {headers})
//             .then(response => {
//               this.notifyList = response.data;
//             })
//             .catch(error => {
//               console.error('Failed to load notify list', error);
//             });
//         },
//         markAsRead(notifySeq) {
//             const notifyIdx = {
//                 "notifySeq" : notifySeq
//             };
//             const headers = {
//                 "Content-Type": "application/json;charset=utf-8",
//                 Authorization: `Bearer ${localStorage.getItem("access-token")}`,
//             };
//           axios.put(`/notify/`, notifyIdx, {headers})
//             .then((response) => {
//               console.log(response);
//               const notify = this.notifications.find(n => n.notifySeq === notifySeq);
//               if (notify) {
//                 notify.readState = true;
//               }
//             })
//             .catch(error => {
//               console.error('Failed to mark as read', error);
//             });
//         }
// }}
</script>

<style scoped>
    .modal-mask {
      position: fixed;
      z-index: 9998;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      display: flex;
      transition: opacity 0.3s ease;
    }

    .modal-container {
      width: 55vw;
      height: 80vh;
      margin: auto;
      padding: 2vw 3vh;
      background-color: #fff;
      border-radius: 2vw;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
      transition: all 0.3s ease;
    }

    .modal-container img {
        width : 1.5vw;
        margin : 1vw;
    }

    .modal-header{
      margin-top: 0;
      display: flex;
      justify-content: right;
    }
    .modal-enter-from {
      opacity: 0;
    }

    .modal-leave-to {
      opacity: 0;
    }

    .modal-enter-from .modal-container,
    .modal-leave-to .modal-container {
      -webkit-transform: scale(1.1);
      transform: scale(1.1);
    }
    .notification-list {
        overflow-y:scroll;
        padding : 0 1vw;
        height : 70vh;
        -ms-overflow-style: none; /* 인터넷 익스플로러 */
        scrollbar-width: none; /* 파이어폭스 */
    }
    .notification-list::-webkit-scrollbar {
        display: none;
    }
    .notification-none {
        height : 50vh;
        display : flex;
        justify-content: center;
        align-items : center;
        font-size : 3.5vh;
    }
    .notification {
        border-top : solid 1px #6A6A6A;
        border-bottom : solid 1px #6A6A6A;
        margin : 1vh 0;
        font-size : 2vh;
        padding : 1vh 1vw;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .nickname {
        color : #82ACC1;
        font-weight: bold;
        margin-right : 0.5vh;
    }

    .ilchon{
        color : #E29714;
        font-weight: bold;
        margin-left : 1vh;
    }
    .acc{
        font-weight:bold;
        margin-left : 1vh;
    }
    .date {
        margin : 0 1vh;
        font-size : 0.8vw;
        color : #6A6A6A;
    }
</style>