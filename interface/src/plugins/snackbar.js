const snackbarPlugin = {
    install: (Vue, { store }) => {
        if (!store) {
            throw new Error('Please provide vuex store.');
        }

        Vue.prototype.$snackbar = {
            showMessage: function ({
                show = false,
                color = "",
                text = "",
                timeout = 0,
            }) {
                store.commit(
                    'showMessage',
                    { show, color, text, timeout },
                    { root: true }
                );
            }
        };
    },
};
export default snackbarPlugin;