<template>
    <div class="wrap">
        <!-- <div class="blank"></div> -->
        <UserTitleComp />
        <div class="right-body">
            <div class="weekly-today"># 주간 TODAY</div>
            <apexchart class="weekly-today-chart" width="94%" height="35%" type="line" :options="options" :series="series">
            </apexchart>
            <div class="row">
                <MyPostChart />
                <MyAchievement />
            </div>
        </div>
    </div>
</template>

<script>
import UserTitleComp from "@/components/BasicComp/UserTitleComp.vue"
import MyPostChart from "@/components/StatisticComp/MyPostChart.vue"
import MyAchievement from "@/components/StatisticComp/MyAchievement.vue"
import VueApexCharts from "vue3-apexcharts";
import http from '@/api/httpWithAccessToken'

export default {
    components: { UserTitleComp, MyPostChart, MyAchievement, apexchart: VueApexCharts },
    created() {
        var userSeq = localStorage.getItem('user-seq');
        http.get(`/statistic/${userSeq}?userSeq=${userSeq}`).then((response) => {
            console.log(response.data.data);
            var temp = response.data.data.weeklyTodayInfoList;
            for (var i = 0; i < 7; i++) {
                if (temp[i] != undefined) {
                    this.weeklyToday.push(temp[i].today);
                    this.weeklyDate.push(temp[i].date);
                }
                else {
                    this.weeklyToday.push(0);
                    this.weeklyDate.push("");
                }
            }

            // this.weeklyToday = this.weeklyToday.reverse();
            // this.weeklyDate = this.weeklyDate.reverse();
            console.log(this.weeklyDate);
            console.log(this.weeklyToday);
            this.likeCnt = response.data.data.likeCnt;
            this.helpfulCnt = response.data.data.helpfulCnt;
            this.understandCnt = response.data.data.understandCnt;
        }, (error) => {
            console.log(error);
            alert("통계 내역 호출 중 오류 발생!");
        })
    },
    data() {
        return {
            weeklyToday: [],
            weeklyDate: [],
            likeCnt: null,
            helpfulCnt: null,
            understandCnt: null,
            options: {
                chart: {
                    toolbar: {
                        show: false,
                    },
                    zoom: {
                        enabled: false,
                    },
                    animations: {
                        enabled: true,
                        speed: 900,
                        animateGradually: {
                            enabled: true,
                            delay: 150
                        },
                        dynamicAnimation: {
                            enabled: true,
                            speed: 350
                        }
                    },
                },
                xaxis: {
                    categories: this.weeklyDate===undefined?[]:this.weeklyDate,
                },

                colors: ['#D9D9D9'],
                fill: {
                    type: 'gradient',
                    gradient: {
                        shadeIntensity: 1,
                        type: "vertical",
                        colorStops: [
                            { offset: 0, color: "#82ACC1", opacity: 1 },
                            { offset: 100, color: "#82ACC1", opacity: 1 }
                        ]
                    }
                }, plotOptions: {
                    bar: { columnWidth: '30%', endingShape: 'rounded', dataLabels: { position: 'center' } }
                },
                tooltip: {
                    enabled: true,
                    shared: false,
                    followCursor: true,
                    fillSeriesColor: true,
                    theme: 'light'
                },
                markers: {
                    size: 4,
                    colors: "#D9D9D9",
                    strokeColors: '#A5A5A5',
                    onClick: undefined,
                    onDblClick: undefined,
                },
            },
            series: [{ name: 'TODAY', data: this.weeklyToday===undefined?[]:this.weeklyToday }],
            
        }
    },
}

</script>

<style scoped>
.blank {
    height: 40px;
}

.right-body {
    background-color: white;
    width: 62vw;
    height: 75vh;
    border: 1px solid #6A6A6A;
    border-radius: 15px;
}

.row {
    /* width: 100%; */
    display: flex;
}

.weekly-today {
    text-align: start;
    color: #6A6A6A;
    font-size: 16px;
    font-weight: 600;
    margin-left: 3%;
    margin-top: 2%;
    margin-bottom: 3%;
}

.weekly-today-chart {
    margin-left: 3%;
}
</style>
