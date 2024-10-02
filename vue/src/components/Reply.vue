<template>
  <div class="thread">
    <!-- "id": 5,
                "postId": 1,
                "root": 0,
                "description": "Thanks! Glad you agree.",
                "created": "2024-10-01T20:15:12.453+00:00",
                "replies": [],
                "user": {
                    "id": 3,
                    "username": "user_one"
                } -->
    <p>{{ reply.user.username }}</p>
    <p>{{ reply.description }}</p>
    <div class="comments" :style="{ marginLeft: `${indent + 20}px` }">
        <reply v-for="comment in reply.replies" :key="comment.id" :reply="comment" :indent="indent+20"></reply>
    </div>
  </div>
</template>

<script>
export default {
    props: ['reply', 'indent'],
    data(){
        return {
            styles: {margin: this.indent}
        }
    }
}
</script>

<style>

</style>