module.exports = {
  root: true,
  env: {
    node: true,
    es2022: true
  },
  'extends': [
    'plugin:vue/vue3-essential',
    'eslint:recommended',
    'plugin:import/recommended'
  ],
  rules: {
    'no-unused-vars': 'off'
  },
  settings: {
    'import/resolver': {
      node: {
        extensions: ['.js', '.jsx', '.ts', '.tsx', '.vue'],
      },
      alias: {
        map: [
          ['@', './src']
        ],
        extensions: ['.js', '.vue']
      },
    },
  },
  // Needed for accessing .env from import.meta.env
  'parserOptions': {
    'ecmaVersion': 2022
  }
}
