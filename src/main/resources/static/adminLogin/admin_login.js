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
               .post("http://localhost:7070/login_creds", loginCreds)
               .then(response => {
               console.log(response)
               if(response.data.valid = true) {
               window.open("http://localhost:7070/admin_page?key=" + response.data.url)
               } else {
               console.log("no")
               }
               })
               .catch(e => (console.log(e)))
           }
       }
})

app.mount("#app")