export async function fetchJson <TDto extends unknown>(url: string) {
  try {
    const response = await fetch(url)
    return response.json() as Promise<TDto>
  }
  catch (error) {
    console.error(`Failed to fetch ${url}`, error)
    throw error
  }
}