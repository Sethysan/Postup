import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'
import {useModalRouter} from "jenesius-vue-modal";
// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import ForumsView from '../views/ForumsView.vue';
import AddForumView from '../views/AddForumView.vue';
import PostView from '../views/PostView.vue';
import ForumView from '../views/ForumView.vue';
import ForumPostView from '../views/ForumPostView.vue';
import CreatePost from '../components/CreatePost.vue';
import Moderator from '../components/Moderator.vue';
import FavoritesView from '../views/FavoritesView.vue';
import ForumPostSearchView from '../views/ForumPostSearchView.vue';
import MessagesView from '../views/MessagesView.vue';
import DirectMessage from '../views/DirectMessage.vue';
import Admin from '../views/AdminView.vue';
import LoggedInMessage from '../components/LoggedInMessage.vue';
import ShareForm from '../components/ShareForm.vue';

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/logged-in',
    name:'logged-in',
    component: useModalRouter(LoggedInMessage),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums",
    name: "forums",
    component: ForumsView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums/favorites",
    name: "favorites",
    component: FavoritesView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/forums/create",
    name: "create-forums",
    component: AddForumView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/forums/:id",
    name: "forum",
    component: ForumView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums/:forumId/posts",
    name: "forum-post",
    component: PostView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums/:forumId/posts/search",
    name: "forum-post-search",
    component: ForumPostSearchView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/forums/:id/create-post',
    name: 'create-post',
    component: CreatePost,
    props: true,
    meta: {
      requiresAuth: true 
    } 
  },
  {
    path: "/posts/:post",
    name: "post",
    component: ForumPostView,
    props: true,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/posts/share",
    name: "share",
    component: ShareForm,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/moderation/forum/:id/promote",
    name: "promote",
    component: Moderator,
    meta: {
      requiresAuth: true
    }
  },
    {
      path: "/messages",
      name: "messages",
      component: MessagesView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/messages/:user",
      name: "direct-messages",
      component: DirectMessage,
      meta: {
        requiresAuth: true
      }
    },
    {
    path: "/users/admin",
    name: "admin",
    component: Admin,
      meta: {
        requiresAuth: true
      }
    }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});


useModalRouter.init(router);

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
