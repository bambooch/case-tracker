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

  useEffect(() => {
    const loadCases = async () => {
      const response = await fetch('/api/cases')
      const caseSummaries = (await response.json()) as CaseSummary[]
      setCases(caseSummaries)
    }

    void loadCases()
  }, [])

  return (
    <main>
      <h1>Cases</h1>
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
