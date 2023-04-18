<template>
    <div id = "categoryinfo">
        <p id = "folder">[전체보기]</p>
        <p style="font-size:1.4vw"> 폴더입니다</p>
        <p id = "cnt">(26)</p>
    </div>
    <table id = "boardlisttable">
        <thead>
            <tr id = "boardheader">
                <th id = "boardNum">번호</th>
                <th id = "boardTitle">제목</th>
                <th id = "boardWriter">작성자</th>
                <th id = "boardDate">작성일</th>
                <th id = "boardCnt">조회수</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td id = "boardNum">10</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">9</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">8</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">7</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">6</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">5</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">4</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">3</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">2</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
            <tr>
                <td id = "boardNum">1</td>
                <td id = "boardTitle">싸이월드 구현하기 프로젝트</td>
                <td id = "boardWriter">김싸피</td>
                <td id = "boardDate">2023.04.19</td>
                <td id = "boardCnt">5</td>
            </tr>
        </tbody>
    </table>
</template>

<script>
</script>

<style scoped>
    #folder {
        font-size : 1.4vw;
        font-weight:bold;
        margin : 0 0.5vw 0 0.5vw;
    }
    #cnt {
        font-size : 1.4vw;
        color : #D7AA71;
        margin-left : 0.5vw;
    }
    #boardlisttable {
        width : 100%;
        padding : 0 3vw 0 3vw;
    }
    #categoryinfo {
        display: flex;
        justify-content: right;
        align-items: center;
        margin : 1vh 3vw 1vh 0;
    }
    #boardheader {
        background-color: #F2F2F2;
        font-size : 1.3vw;
        font-weight : bolder;
    }
    thead {
        padding : 0.5vh 0 0.5vh 0;
    }

    tr {
        padding : 1vh 0 1vh 0;
        margin : 1vh 0 1vh 0;
        border-style: dashed;
        border-bottom : 1px;
        border : black;
    }
    tbody tr{
        border-style: dashed;
        border-bottom : 1px;
        border : black;
    }
</style>