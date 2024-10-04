<template>
  <div class="thread">
    <p>{{ reply.user.username }}</p>
    <p>{{ reply.description }}</p>
    <button v-if="reply.user.username === user" class="deletePost" @click="deletePost">Delete</button>
    <button @click="() => { formVisibility = true }">Reply</button>
      <div v-if="formVisibility">
        <form v-on:submit.prevent="addReply">
            <textarea v-model="newReply.description"></textarea>
                <button type="submit">Sumbit</button>
                <button :onclick="() => { formVisibility = false; newReply = {} }">Cancel</button>
          </form>
       </div>
    <div class="comments" :style="{ marginLeft: `${indent + 20}px` }">
        <reply v-for="comment in reply.replies" :key="comment.id" :reply="comment" :indent="indent+20"></reply>
    </div>
  </div>
</template>

<script>
import replySerive from '../services/RepliesService';
export default {
    props: ['reply', 'indent'],
    data(){
        return {
            styles: {margin: this.indent},
            formVisibility: false,
            newReply: {
              respondsTo: this.reply.id,
              description : ""
            }
        }
    },
    methods: {
      addReply() {
        if(this.newReply.description != ""){
            replySerive.createReply(this.reply.postId, this.newReply)
                .then(res => {
                    this.reply.replies.push(res.data);
                    this.newReply = {}
                    this.formVisibility = false;
                })
                .catch(err => alert("error "))
              }
          }
    }
}
</script>

<style>

</style>