export async function fetchJson <TDto extends unknown>(url: string) {
  try {
    const response = await fetch(url)
    if (!response.ok) {
      throw new Error(`Unexpected response from ${url} (status ${response.status}: ${response.statusText})`)
    }
    return response.json() as Promise<TDto>
  }
  catch (error) {
    console.error(`Failed to fetch ${url}`, error)
    throw error
  }
}