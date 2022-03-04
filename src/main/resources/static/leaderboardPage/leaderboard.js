const app = Vue.createApp ({
    data() {
        return {
        leaders: null
        }
    },
    mounted() {
    axios
    //localhost:7070/audio_text
    //https://andrew-pinner.asuscomm.com/audio_text/leaderboard
    .get("https://andrew-pinner.asuscomm.com/audio_text/leaderboard")
    .then(res => (this.leaders = res.data))
    .catch(e =>(console.log(e)))
    }
})

app.mount("#app")
