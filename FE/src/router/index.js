import {createRouter, createWebHistory} from 'vue-router';

import BoardListView from '../views/BoardList.vue'
import GuestBookListView from '../views/GuestBookList.vue';

const routes = [
    {path:"/boardlist", component:BoardListView},
    {path:"/guestbook", component:GuestBookListView}
]

const router = createRouter({
    history: createWebHistory(),
    routes
});

export {router}