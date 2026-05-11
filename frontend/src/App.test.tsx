import { render, screen, within } from '@testing-library/react'
import { describe, expect, it, vi } from 'vitest'
import App from './App'

describe('App', () => {
  it('renders cases returned by the API', async () => {
    vi.spyOn(globalThis, 'fetch').mockResolvedValue({
      json: async () => [
        {
          id: 1,
          title: 'Missing documents',
          status: 'OPEN',
          attentionLevel: 'IMMEDIATE',
        },
      ],
    } as Response)

    render(<App />)

    expect(await screen.findByText('Missing documents')).toBeInTheDocument()
    expect(screen.getByText('OPEN')).toBeInTheDocument()
  })

  it('renders the case list without bullets', async () => {
    vi.spyOn(globalThis, 'fetch').mockResolvedValue({
      json: async () => [
        {
          id: 1,
          title: 'Missing documents',
          status: 'OPEN',
          attentionLevel: 'IMMEDIATE',
        },
      ],
    } as Response)

    render(<App />)

    await screen.findByText('Missing documents')

    const main = screen.getByRole('main')
    const list = within(main).getByRole('list')
    
    expect(getComputedStyle(list).listStyleType).toBe('none')
  })
})