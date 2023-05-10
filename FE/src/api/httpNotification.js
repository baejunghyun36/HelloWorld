import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "https://k8a308.p.ssafy.io/notify",
  headers: {
        "Content-Type": "application/json;charset=utf-8",
        Authorization: `Bearer ${localStorage.getItem("access-token")}`,
        "Connection": "keep-alive",
        "Accept": "text/event-stream",
        "Cache-Control": "no-cache",
  },
});