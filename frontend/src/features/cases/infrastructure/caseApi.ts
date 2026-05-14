import type { CaseDetail } from '../domain/caseDetail'
import type { CaseDraft } from '../domain/caseDraft'
import type { CaseSummary } from '../domain/caseSummary'

export async function listCases() {
  const response = await fetch('/api/cases')
  return (await response.json()) as CaseSummary[]
}

export async function createCase(caseDraft: CaseDraft) {
  const response = await fetch('/api/cases', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(caseDraft),
  })

  if (!response.ok) {
    throw new Error('Could not create case.')
  }

  return (await response.json()) as CaseSummary
}

export async function updateCase(caseId: number, caseDraft: CaseDraft) {
  const response = await fetch(`/api/cases/${caseId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(caseDraft),
  })

  if (!response.ok) {
    throw new Error('Could not update case.')
  }

  return (await response.json()) as CaseSummary
}

export async function deleteCase(caseId: number) {
  const response = await fetch(`/api/cases/${caseId}`, { method: 'DELETE' })

  if (!response.ok) {
    throw new Error('Could not delete case.')
  }
}

export async function getCaseDetail(caseId: number) {
  const response = await fetch(`/api/cases/${caseId}`)

  if (!response.ok) {
    throw new Error('Could not load case detail.')
  }

  return (await response.json()) as CaseDetail
}
