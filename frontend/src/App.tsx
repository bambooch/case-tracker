import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'

import { NavBar } from './components/NavBar'
import { CaseDetailPage } from './features/cases/ui/CaseDetailPage'
import { CasesPage } from './features/cases/ui/CasesPage'
import { PartiesPage } from './features/parties/ui/PartiesPage'

function App() {
  return (
    <BrowserRouter>
      <div className="flex min-h-screen flex-col">
        <NavBar />
        <main className="flex-1">
          <Routes>
            <Route path="/" element={<Navigate to="/cases" replace />} />
            <Route path="/cases" element={<CasesPage />} />
            <Route path="/cases/:id" element={<CaseDetailPage />} />
            <Route path="/parties" element={<PartiesPage />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  )
}

export default App
