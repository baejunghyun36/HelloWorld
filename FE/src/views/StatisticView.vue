<template>
    <div id = "line">
        <div id = "Board">
            <UserProfile />
            <div id = "MainBoard">
                <StatisticComp/>
                <CategoryNav/>
            </div>
        </div>
    </div>
</template>

<script>
import UserProfile from "@/components/MainPageComp/UserProfile.vue"
import StatisticComp from "@/components/StatisticComp/StatisticMain.vue"
import CategoryNav from "@/components/BasicComp/CategoryNav.vue"

export default {
    components: { UserProfile, StatisticComp, CategoryNav },
};
</script>


<style scoped>
    #line {
        width: 90vw;
        height: 90vh;
        margin: 0 auto;
        margin-top: 5vh;
        border: 3px dashed #6A6A6A;
        border-radius: 10px;
    }

    #Board {
        display : flex;
        align-items: center;
        justify-content: center;
        width: 88vw;
        height: 86vh;
        margin: 0 auto;
        margin-top: 2vh;
        background-color: #F0F0F0;
        border-radius: 10px;
    }
    #MainBoard{
        display : flex;
        align-items: center;
        justify-content: center;
    }
</style>

<!-- <template>
    <div class="dashed-border">
        <div class="grey-bg">
            <UserProfile />
            <StatisticPage />
            <div style="margin-top: 80px;">
                <CategoryNav />
            </div>
        </div>

    </div>
</template>

<script>
import UserProfile from "@/components/MainPageComp/UserProfile.vue"
import StatisticPage from "@/components/StatisticComp/StatisticMain.vue"
import CategoryNav from "@/components/BasicComp/CategoryNav.vue"

export default {
    components: { UserProfile, StatisticPage, CategoryNav },
};
</script>


<style scoped>
.dashed-border {
    width: 90vw;
    height: 90vh;
    margin: 0 auto;
    margin-top: 5vh;
    border: 3px dashed #6A6A6A;
    border-radius: 10px;
}

.grey-bg {
    width: 88vw;
    height: 86vh;
    margin: 0 auto;
    margin-top: 2vh;
    background-color: #F0F0F0;
    border-radius: 10px;
    display: flex;
}
</style> -->