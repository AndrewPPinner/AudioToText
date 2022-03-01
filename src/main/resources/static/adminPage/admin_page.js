const app = Vue.createApp ({
    data() {
        return {
        winningWord: null,
        winningCount: null,
        isLoading: false
        }
    },
    methods: {
           getUpdate() {
           this.isLoading = true;
                axios
                .get("http://localhost:7070/admin_page/update")
                .then(res => (this.winningWord = res.data.word, this.winningCount = res.data.count, this.isLoading = false))
                .catch(e => (console.log(e)))
           }
       }
})

app.mount("#app")