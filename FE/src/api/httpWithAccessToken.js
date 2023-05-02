import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "https://k8a308.p.ssafy.io/api",
  headers: {
    "Content-Type": "application/json;charset=utf-8",
    Authorization: `Bearer ${window.localStorage.getItem("access-token")}`,
  },
});

