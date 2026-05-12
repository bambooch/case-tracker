import { render, screen, within } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
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

  it('renders a create case form', async () => {
    vi.spyOn(globalThis, 'fetch').mockResolvedValue({
      json: async () => [],
    } as Response)

    render(<App />)

    await screen.findByRole('heading', { name: 'Cases' })

    expect(screen.getByLabelText('Title')).toBeInTheDocument()
    expect(screen.getByLabelText('Status')).toBeInTheDocument()
    expect(screen.getByRole('button', { name: 'Create case' })).toBeInTheDocument()
  })

  it('submits the title and status for a new case', async () => {
    const fetchSpy = vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce({
        json: async () => [],
      } as Response)
      .mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          id: 7,
          title: 'Missing intake form',
          status: 'OPEN',
          attentionLevel: 'SOON',
        }),
      } as Response)

    render(<App />)

    const user = userEvent.setup()

    await user.type(screen.getByLabelText('Title'), 'Missing intake form')
    await user.selectOptions(screen.getByLabelText('Status'), 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Create case' }))

    expect(fetchSpy).toHaveBeenNthCalledWith(
      2,
      '/api/cases',
      expect.objectContaining({
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: 'Missing intake form',
          status: 'OPEN',
        }),
      }),
    )
  })

  it('shows the new case in the list after a successful create', async () => {
    vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce({
        json: async () => [],
      } as Response)
      .mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          id: 7,
          title: 'Missing intake form',
          status: 'OPEN',
          attentionLevel: 'SOON',
        }),
      } as Response)

    render(<App />)

    const user = userEvent.setup()

    await user.type(screen.getByLabelText('Title'), 'Missing intake form')
    await user.selectOptions(screen.getByLabelText('Status'), 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Create case' }))

    expect(await screen.findByText('Missing intake form')).toBeInTheDocument()
    expect(screen.getByText('OPEN')).toBeInTheDocument()
  })

  it('shows an error message when creating a case fails', async () => {
    vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce({
        json: async () => [],
      } as Response)
      .mockRejectedValueOnce(new Error('Request failed'))

    render(<App />)

    const user = userEvent.setup()

    await user.type(screen.getByLabelText('Title'), 'Missing intake form')
    await user.selectOptions(screen.getByLabelText('Status'), 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Create case' }))

    expect(await screen.findByText('Could not create case.')).toBeInTheDocument()
  })

  it('shows an error message when create returns a non-OK response', async () => {
    vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce({
        json: async () => [],
      } as Response)
      .mockResolvedValueOnce({
        ok: false,
        json: async () => ({ message: 'Validation failed' }),
      } as Response)

    render(<App />)

    const user = userEvent.setup()

    await user.type(screen.getByLabelText('Title'), 'Missing intake form')
    await user.selectOptions(screen.getByLabelText('Status'), 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Create case' }))

    expect(await screen.findByText('Could not create case.')).toBeInTheDocument()
  })

  it('clears the create form after a successful create', async () => {
    vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce({
        json: async () => [],
      } as Response)
      .mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          id: 7,
          title: 'Missing intake form',
          status: 'OPEN',
          attentionLevel: 'SOON',
        }),
      } as Response)

    render(<App />)

    const user = userEvent.setup()
    const titleInput = screen.getByLabelText('Title') as HTMLInputElement
    const statusSelect = screen.getByLabelText('Status') as HTMLSelectElement

    await user.type(titleInput, 'Missing intake form')
    await user.selectOptions(statusSelect, 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Create case' }))

    await screen.findByText('Missing intake form')

    expect(titleInput).toHaveValue('')
    expect(statusSelect).toHaveValue('')
  })
})