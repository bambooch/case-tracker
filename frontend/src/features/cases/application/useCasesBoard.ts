import { useEffect, useState } from 'react'

import { emptyCaseDraft, type CaseDraft } from '../domain/caseDraft'
import type { CaseSummary } from '../domain/caseSummary'
import {
  createCase,
  deleteCase,
  listCases,
  updateCase,
} from '../infrastructure/caseApi'

type CaseBoardErrors = {
  create: string
  edit: string
  delete: string
}

const emptyErrors: CaseBoardErrors = {
  create: '',
  edit: '',
  delete: '',
}

export function useCasesBoard() {
  const [cases, setCases] = useState<CaseSummary[]>([])
  const [createDraft, setCreateDraft] = useState<CaseDraft>(emptyCaseDraft)
  const [editingCaseId, setEditingCaseId] = useState<number | null>(null)
  const [editDraft, setEditDraft] = useState<CaseDraft>(emptyCaseDraft)
  const [errors, setErrors] = useState<CaseBoardErrors>(emptyErrors)

  useEffect(() => {
    const load = async () => {
      setCases(await listCases())
    }

    void load()
  }, [])

  async function submitCreate(): Promise<boolean> {
    setErrors((currentErrors) => ({ ...currentErrors, create: '' }))

    try {
      const createdCase = await createCase(createDraft)
      setCases((currentCases) => [...currentCases, createdCase])
      setCreateDraft(emptyCaseDraft)
      return true
    } catch {
      setErrors((currentErrors) => ({ ...currentErrors, create: 'Could not create case.' }))
      return false
    }
  }

  function startEditing(caseSummary: CaseSummary) {
    setEditingCaseId(caseSummary.id)
    setEditDraft({
      title: caseSummary.title,
      status: caseSummary.status as CaseDraft['status'],
    })
    setErrors((currentErrors) => ({ ...currentErrors, edit: '' }))
  }

  function cancelEditing() {
    setEditingCaseId(null)
    setEditDraft(emptyCaseDraft)
  }

  async function submitEdit(): Promise<boolean> {
    if (editingCaseId === null) {
      return false
    }

    setErrors((currentErrors) => ({ ...currentErrors, edit: '' }))

    try {
      const updatedCase = await updateCase(editingCaseId, editDraft)
      setCases((currentCases) =>
        currentCases.map((caseSummary) =>
          caseSummary.id === updatedCase.id ? updatedCase : caseSummary,
        ),
      )
      cancelEditing()
      return true
    } catch {
      setErrors((currentErrors) => ({ ...currentErrors, edit: 'Could not update case.' }))
      return false
    }
  }

  async function removeCase(caseId: number) {
    setErrors((currentErrors) => ({ ...currentErrors, delete: '' }))

    try {
      await deleteCase(caseId)
      setCases((currentCases) => currentCases.filter((caseSummary) => caseSummary.id !== caseId))

      if (editingCaseId === caseId) {
        cancelEditing()
      }
    } catch {
      setErrors((currentErrors) => ({ ...currentErrors, delete: 'Could not delete case.' }))
    }
  }

  return {
    cases,
    createDraft,
    setCreateDraft,
    editingCaseId,
    editDraft,
    setEditDraft,
    errors,
    submitCreate,
    startEditing,
    cancelEditing,
    submitEdit,
    removeCase,
  }
}
