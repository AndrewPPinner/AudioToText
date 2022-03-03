const app = Vue.createApp ({
    data() {
        return {
        login: "",
        password: ""
        }
    },
    methods: {
           redirect(login,password) {
           const loginCreds = [login, password]
               axios
               .post("https://andrew-pinner.asuscomm.com/audio_text/login_creds", loginCreds)
               .then(response => {
               console.log(response)
               if(response.data.valid = true) {
               window.open("https://andrew-pinner.asuscomm.com/audio_text/admin_page?key=" + response.data.url)
               } else {
               console.log("no")
               }
               })
               .catch(e => (console.log(e)))
           }
       }
})

app.mount("#app")