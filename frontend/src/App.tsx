import { useEffect, useState } from 'react'
import './App.css'

type CaseSummary = {
  id: number
  title: string
  status: string
  attentionLevel: string
}

function App() {
  const [cases, setCases] = useState<CaseSummary[]>([])
  const [title, setTitle] = useState('')
  const [status, setStatus] = useState('')
  const [createError, setCreateError] = useState('')

  useEffect(() => {
    const loadCases = async () => {
      const response = await fetch('/api/cases')
      const caseSummaries = (await response.json()) as CaseSummary[]
      setCases(caseSummaries)
    }

    void loadCases()
  }, [])

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    setCreateError('')

    try {
      const response = await fetch('/api/cases', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ title, status }),
      })

      if (response.ok) {
        const createdCase = (await response.json()) as CaseSummary
        setCases((currentCases) => [...currentCases, createdCase])
        setTitle('')
        setStatus('')
      } else {
        throw new Error('Could not create case.')
      }

    } catch {
      setCreateError('Could not create case.')
    }
  }

  return (
    <main>
      <h1>Cases</h1>
      <form onSubmit={(event) => void handleSubmit(event)}>
        <div>
          <label htmlFor="case-title">Title</label>
          <input
            id="case-title"
            name="title"
            type="text"
            value={title}
            onChange={(event) => setTitle(event.target.value)}
          />
        </div>
        <div>
          <label htmlFor="case-status">Status</label>
          <select
            id="case-status"
            name="status"
            value={status}
            onChange={(event) => setStatus(event.target.value)}
          >
            <option value="" disabled>
              Select status
            </option>
            <option value="OPEN">Open</option>
          </select>
        </div>
        <button type="submit">Create case</button>
      </form>
      {createError ? <p>{createError}</p> : null}
      <ul style={{ listStyleType: 'none', margin: 0, padding: 0 }}>
        {cases.map((caseSummary) => (
          <li key={caseSummary.id}>
            <h2>{caseSummary.title}</h2>
            <p>{caseSummary.status}</p>
          </li>
        ))}
      </ul>
    </main>
  )
}

export default App
