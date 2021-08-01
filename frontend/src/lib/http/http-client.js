const BASE_API_PATH = 'http://localhost:8080/api/'

export const request = async (path) => {
  try {
    const data = await fetch(`${BASE_API_PATH}${path}`, { headers: { cors: 'no-cors' } })
    const response = await data.json()

    return response
  } catch (e) {
    console.log(e.message)
  }
}
