import { render, screen, waitFor, within } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, expect, it, vi } from 'vitest'
import App from './App'

function renderAt(path = '/cases') {
  window.history.pushState({}, '', path)
  render(<App />)
}

function createJsonResponse(body: unknown, ok = true) {
  return {
    ok,
    json: async () => body,
  } as Response
}

describe('App', () => {
  it('renders cases returned by the API', async () => {
    vi.spyOn(globalThis, 'fetch').mockResolvedValue(createJsonResponse([
        {
          id: 1,
          title: 'Missing documents',
          status: 'OPEN',
          attentionLevel: 'IMMEDIATE',
        },
      ]))

    renderAt()

    expect(await screen.findByText('Missing documents')).toBeInTheDocument()
    expect(screen.getByText('OPEN')).toBeInTheDocument()
  })

  it('renders the case list without bullets', async () => {
    vi.spyOn(globalThis, 'fetch').mockResolvedValue(createJsonResponse([
        {
          id: 1,
          title: 'Missing documents',
          status: 'OPEN',
          attentionLevel: 'IMMEDIATE',
        },
      ]))

    renderAt()

    await screen.findByText('Missing documents')

    const main = screen.getByRole('main')
    const list = within(main).getByRole('list')
    
    expect(getComputedStyle(list).listStyleType).toBe('none')
  })

  it('renders a create case form', async () => {
    vi.spyOn(globalThis, 'fetch').mockResolvedValue(createJsonResponse([]))

    renderAt()

    const user = userEvent.setup()

    await screen.findByRole('heading', { name: 'Aktivni predmeti' })
    await user.click(screen.getByRole('button', { name: '+ Novi predmet' }))

    expect(screen.getByRole('heading', { name: 'Novi predmet' })).toBeInTheDocument()
    expect(screen.getByLabelText('Naziv')).toBeInTheDocument()
    expect(screen.getByLabelText('Status')).toBeInTheDocument()
    expect(screen.getByRole('button', { name: 'Kreiraj predmet' })).toBeInTheDocument()
  })

  it('submits the title and status for a new case', async () => {
    const fetchSpy = vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce(createJsonResponse([]))
      .mockResolvedValueOnce(createJsonResponse({
        id: 7,
        title: 'Missing intake form',
        status: 'OPEN',
        attentionLevel: 'SOON',
      }))

    renderAt()

    const user = userEvent.setup()

    await screen.findByRole('heading', { name: 'Aktivni predmeti' })
    await user.click(screen.getByRole('button', { name: '+ Novi predmet' }))
    await user.type(screen.getByLabelText('Naziv'), 'Missing intake form')
    await user.selectOptions(screen.getByLabelText('Status'), 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Kreiraj predmet' }))

    expect(fetchSpy).toHaveBeenCalledWith(
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
      .mockResolvedValueOnce(createJsonResponse([]))
      .mockResolvedValueOnce(createJsonResponse({
        id: 7,
        title: 'Missing intake form',
        status: 'OPEN',
        attentionLevel: 'SOON',
      }))

    renderAt()

    const user = userEvent.setup()

    await screen.findByRole('heading', { name: 'Aktivni predmeti' })
    await user.click(screen.getByRole('button', { name: '+ Novi predmet' }))
    await user.type(screen.getByLabelText('Naziv'), 'Missing intake form')
    await user.selectOptions(screen.getByLabelText('Status'), 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Kreiraj predmet' }))

    expect(await screen.findByText('Missing intake form')).toBeInTheDocument()
    expect(screen.getByText('OPEN')).toBeInTheDocument()
  })

  it('shows an error message when creating a case fails', async () => {
    vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce(createJsonResponse([]))
      .mockRejectedValueOnce(new Error('Request failed'))

    renderAt()

    const user = userEvent.setup()

    await screen.findByRole('heading', { name: 'Aktivni predmeti' })
    await user.click(screen.getByRole('button', { name: '+ Novi predmet' }))
    await user.type(screen.getByLabelText('Naziv'), 'Missing intake form')
    await user.selectOptions(screen.getByLabelText('Status'), 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Kreiraj predmet' }))

    expect(await screen.findByText('Could not create case.')).toBeInTheDocument()
  })

  it('shows an error message when create returns a non-OK response', async () => {
    vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce(createJsonResponse([]))
      .mockResolvedValueOnce(createJsonResponse({ message: 'Validation failed' }, false))

    renderAt()

    const user = userEvent.setup()

    await screen.findByRole('heading', { name: 'Aktivni predmeti' })
    await user.click(screen.getByRole('button', { name: '+ Novi predmet' }))
    await user.type(screen.getByLabelText('Naziv'), 'Missing intake form')
    await user.selectOptions(screen.getByLabelText('Status'), 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Kreiraj predmet' }))

    expect(await screen.findByText('Could not create case.')).toBeInTheDocument()
  })

  it('clears the create form after a successful create', async () => {
    vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce(createJsonResponse([]))
      .mockResolvedValueOnce(createJsonResponse({
        id: 7,
        title: 'Missing intake form',
        status: 'OPEN',
        attentionLevel: 'SOON',
      }))

    renderAt()

    const user = userEvent.setup()

    await screen.findByRole('heading', { name: 'Aktivni predmeti' })
    await user.click(screen.getByRole('button', { name: '+ Novi predmet' }))

    const titleInput = screen.getByLabelText('Naziv') as HTMLInputElement
    const statusSelect = screen.getByLabelText('Status') as HTMLSelectElement

    await user.type(titleInput, 'Missing intake form')
    await user.selectOptions(statusSelect, 'OPEN')
    await user.click(screen.getByRole('button', { name: 'Kreiraj predmet' }))

    await screen.findByText('Missing intake form')

    await waitFor(() => {
      expect(screen.queryByRole('heading', { name: 'Novi predmet' })).not.toBeInTheDocument()
    })

    await user.click(screen.getByRole('button', { name: '+ Novi predmet' }))

    expect(screen.getByLabelText('Naziv')).toHaveValue('')
    expect(screen.getByLabelText('Status')).toHaveValue('')
  })

  it('updates a case from the list', async () => {
    const fetchSpy = vi.spyOn(globalThis, 'fetch').mockImplementation(async (input, init) => {
      const url = typeof input === 'string' ? input : input.toString()

      if (url === '/api/parties') {
        return createJsonResponse([])
      }

      if (url === '/api/cases/1' && !init?.method) {
        return createJsonResponse({
          id: 1,
          title: 'Missing documents',
          status: 'OPEN',
          attentionLevel: 'IMMEDIATE',
          notes: [],
          participants: [],
        })
      }

      if (url === '/api/cases/1' && init?.method === 'PUT') {
        return createJsonResponse({
          id: 1,
          title: 'Updated documents',
          status: 'IN_REVIEW',
          attentionLevel: 'FOLLOW_UP',
        })
      }

      throw new Error(`Unexpected fetch call: ${String(url)}`)
    })

    renderAt('/cases/1')

    const user = userEvent.setup()

    await screen.findByRole('heading', { name: 'Missing documents' })

    await user.click(screen.getByRole('button', { name: 'Uredi' }))
    await user.clear(screen.getByLabelText('Naziv'))
    await user.type(screen.getByLabelText('Naziv'), 'Updated documents')
    await user.selectOptions(screen.getByLabelText('Status'), 'IN_REVIEW')
    await user.click(screen.getByRole('button', { name: 'Spremi izmjene' }))

    expect(fetchSpy).toHaveBeenCalledWith(
      '/api/cases/1',
      expect.objectContaining({
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: 'Updated documents',
          status: 'IN_REVIEW',
        }),
      }),
    )

    expect(await screen.findByText('Updated documents')).toBeInTheDocument()
    expect(screen.getByText('IN_REVIEW')).toBeInTheDocument()
  })

  it('deletes a case from the list', async () => {
    const fetchSpy = vi.spyOn(globalThis, 'fetch')
      .mockResolvedValueOnce(createJsonResponse([
          {
            id: 1,
            title: 'Missing documents',
            status: 'OPEN',
            attentionLevel: 'IMMEDIATE',
          },
        ]))
      .mockResolvedValueOnce({ ok: true } as Response)

    renderAt()

    const user = userEvent.setup()

    await screen.findByText('Missing documents')

    await user.click(screen.getByRole('button', { name: 'Obriši' }))
    await user.click(screen.getByRole('button', { name: 'Potvrdi' }))

    expect(fetchSpy).toHaveBeenCalledWith('/api/cases/1', { method: 'DELETE' })

    await waitFor(() => {
      expect(screen.queryByText('Missing documents')).not.toBeInTheDocument()
    })
  })
})