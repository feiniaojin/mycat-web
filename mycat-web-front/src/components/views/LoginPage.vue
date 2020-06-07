<template>
  <v-container>
    <v-row class="justify-center">
      <span>Welcome Mycat + YOU</span>
    </v-row>
    <v-row class="justify-center">
      <v-form ref="form" v-model="valid" lazy-validation>
        <slot></slot>
        <v-text-field
          v-model="username"
          :counter="10"
          :rules="[v => !!v || '请正确填写']"
          label="名字"
          required
        ></v-text-field>

        <v-text-field
          v-model="password"
          :rules="[v => !!v || '请正确填写']"
          label="密码"
          required
        ></v-text-field>
        <v-container>
          <v-btn color="error" class="mr-4" @click="reset">重置</v-btn>
          <v-btn color="warning" :loading="querying" @click="login()"
            >登陆</v-btn
          >
        </v-container>
      </v-form>
    </v-row>
  </v-container>
</template>
<script>
const localStorge = require('store');
export default {
  props: ['saveact'],
  data: () => ({
    valid: true,
    username: '',
    password: '',
    querying: false
  }),

  methods: {
    validate() {
      this.$refs.form.validate()
    },
    reset() {
      this.$refs.form.reset()
      this.$refs.form.resetValidation()
    },
    async login() {
      this.valid = this.$refs.form.validate()
      if (!this.valid) {
        console.log('not valid ')
      } else {
        // 发出登陆请求
        // console.log(JSON.stringify(this.data))
        // console.log(this.username)
        // console.log(this.password)
        let resp
        try {
          this.querying = true
          resp = await this.axioscall.post(
            // this.comutil.Constant + '/Login',
            '/login',
            JSON.stringify({
              username: this.username,
              password: this.password
            })
          )
          console.log( resp)
          console.log('-------')
          // console.log(' resp  ' + JSON.stringify(resp))
          // console.log(' resp.data.token  ' + resp.data.data.token)
          // console.log(' resp.data.user.username  ' + resp.data.data.user.username)
          let logicData = resp.data
          if (logicData.code !== 0) {
            this.comutil.MessageBox.show(logicData.msg)
            return
          }
          this.comutil.Token.setNewToken({
            token: logicData.data.token,
            expireTime: new Date(logicData.expireTime),
            refreshToken: logicData.data.token,
            user: {
              name: logicData.data.user.username,
              roles: ['user', 'admin']
            }
          })
          localStorge.set('token',logicData.data.token)
          this.$router.push('/MainPage')
        } catch (e) {
          console.log(' exec call error ' + e)
          this.comutil.MessageBox.show(e)
        } finally {
          this.querying = false
        }
      }
    }
  }
}
</script>
