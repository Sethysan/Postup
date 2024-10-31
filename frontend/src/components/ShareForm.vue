<template>
    <div>
        <p>Choose A Destination</p>
        <div class="social-list">
            <share-network
                v-for="network in networks"
                :key="network.network"
                v-slot="{ share }"
                :network="network.network"
                :title="sharingInfo.title"
                :url="sharingInfo.url"
                :description="sharingInfo.description"
                :quote="sharingInfo.quote"
                :media="sharingInfo.media"
            >
                <div
                class="social-network"
                @click="share"
                :style="{ backgroundColor: network.color }"
                >
                <i :class="network.icon">{{ network.network }}</i>
                </div>
            </share-network>
      </div>
    </div>
</template>

<script>  
import { defineComponent } from 'vue';
// Import vue3-social-sharing as a CommonJS module
import { ShareNetwork } from 'vue3-social-sharing';
export default {
  components: {
    'share-network': ShareNetwork,
  },
    props: {
        url: {
            type: String,
            required: true,
        },
        post: {
            type: Object,
            required: true,
        }
    },
    data(){
        return {
        networks : [
            {
                network: 'facebook',
                name: 'Facebook',
                icon: 'fab fa-lg fa-facebook-f',
                color: '#1877f2',
            },
            {
                network: 'linkedin',
                name: 'LinkedIn',
                icon: 'fab fa-lg fa-linkedin',
                color: '#007bb5',
            },
            {
                network: 'messenger',
                name: 'Messenger',
                icon: 'fab fa-lg fa-facebook-messenger',
                color: '#0084ff',
            },
            {
                network: 'pinterest',
                name: 'Pinterest',
                icon: 'fab fah fa-lg fa-pinterest',
                color: '#bd081c',
            },
            {
                network: 'quora',
                name: 'Quora',
                icon: 'fab fah fa-lg fa-quora',
                color: '#a82400',
            },
            {
                network: 'reddit',
                name: 'Reddit',
                icon: 'fab fah fa-lg fa-reddit-alien',
                color: '#ff4500',
            },
            {
                network: 'skype',
                name: 'Skype',
                icon: 'fab fah fa-lg fa-skype',
                color: '#00aff0',
            },
            {
                network: 'tumblr',
                name: 'Tumblr',
                icon: 'fab fah fa-lg fa-tumblr',
                color: '#35465c',
            },
            {
                network: 'x',
                name: 'X',
                icon: 'fab fah fa-lg fa-twitter',
                color: '#000000',
            },
            {
                network: 'whatsapp',
                name: 'Whatsapp',
                icon: 'fab fah fa-lg fa-whatsapp',
                color: '#25d366',
            },
        ]
        }
    },
    computed: {
    sharingInfo() {
        return {
            url: this.url,
            title: 'Check out this cool post on Post-Up!',
            description: this.post.title || 'No title available',
            quote: this.post.description ? this.post.description.substring(0, 20) : '',
            media: this.post.image || ''
        };
    }
}

}
</script>

<style scoped>
.social-list {
    display: flex;
    flex-wrap: wrap;
    gap: 0.6rem;
}
.social-network {
    cursor: pointer;
    padding: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 5px;
}

</style>