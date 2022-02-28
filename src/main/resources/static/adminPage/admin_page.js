const app = Vue.createApp ({
    data() {
        return {
        winningWord: null,
        winningCount: null
        }
    },
    methods: {
           getUpdate() {
                axios
                .get("http://localhost:7070/admin_page/update")
                .then(res => (this.winningWord = res.data.word, this.winningCount = res.data.count))
                .catch(e => (console.log(e)))
           }
       }
})

app.mount("#app")