const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  username: state => state.user.username,
  routes: state => state.router.routes
}
export default getters
