import axios from "axios";

const instance = axios.create({
    baseURL: 'http://localhost:9091/api',
    headers: {
        'Access-Control-Allow-Origin' : '*',
        'Authorization': localStorage.getItem("JWT")
    }
})

export default instance;