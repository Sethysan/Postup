<template>
  <div>
    <button :onclick="() => {showOptions = true}">
        <p v-if="!updateMode">{{ message.message }}</p>
        <textarea v-if="updateMode" v-model="message.message"></textarea>
        <button v-if="updateMode" :onclick="update">Update</button>
        <p>{{ message.sender.username }}</p>
        <p>sent {{ message.receivedOn }}</p>
        <p v-if="message.read">read A CHECK HERE</p>
    </button>
    <div v-if="showOptions">
    <!-- <button :onclick="">Edit</button> -->
        <button :onclick="deleteMessage">Delete</button>
        <button :onclick="()=>{updateMode = true}">Edit</button>
    </div>
  </div>
</template>

<script>
import service from '../services/MessageService'
export default {
    props: ['message'],
    data(){
        return {
            showOptions: false,
            updateMode: false,
        }
    },
    methods: {
    deleteMessage(){
        service.deleteMessage(this.message.sender.id, this.message.id)
            .then(res => {
                // force a rerender
                this.message.message = "this message has been removed"
            })
            .catch(err => alert(err.message));
    },
    update(){
        service.updateMessage(this.message.sender.id, this.message.id, this.message)
            .then(res => {
                this.updateMode = false;
            })
            .catch(err => alert(err.message));
    }
}

}
</script>

<style>

</style>