import { useEffect, useState } from 'react'

import { emptyPartyDraft, type Party, type PartyDraft } from '../domain/party'
import { createParty, deleteParty, listParties, updateParty } from '../infrastructure/partyApi'

type PartyErrors = { create: string; edit: string; delete: string }
const emptyErrors: PartyErrors = { create: '', edit: '', delete: '' }

export function useParties() {
  const [parties, setParties] = useState<Party[]>([])
  const [createDraft, setCreateDraft] = useState<PartyDraft>(emptyPartyDraft)
  const [editingPartyId, setEditingPartyId] = useState<number | null>(null)
  const [editDraft, setEditDraft] = useState<PartyDraft>(emptyPartyDraft)
  const [errors, setErrors] = useState<PartyErrors>(emptyErrors)

  useEffect(() => {
    const load = async () => {
      setParties(await listParties())
    }
    void load()
  }, [])

  async function submitCreate() {
    setErrors((e) => ({ ...e, create: '' }))
    try {
      const party = await createParty(createDraft)
      setParties((prev) => [...prev, party])
      setCreateDraft(emptyPartyDraft)
    } catch {
      setErrors((e) => ({ ...e, create: 'Could not create party.' }))
    }
  }

  function startEditing(party: Party) {
    setEditingPartyId(party.id)
    setEditDraft({ name: party.name, email: party.email ?? '' })
    setErrors((e) => ({ ...e, edit: '' }))
  }

  function cancelEditing() {
    setEditingPartyId(null)
    setEditDraft(emptyPartyDraft)
  }

  async function submitEdit() {
    if (editingPartyId === null) return
    setErrors((e) => ({ ...e, edit: '' }))
    try {
      const updated = await updateParty(editingPartyId, editDraft)
      setParties((prev) => prev.map((p) => (p.id === updated.id ? updated : p)))
      cancelEditing()
    } catch {
      setErrors((e) => ({ ...e, edit: 'Could not update party.' }))
    }
  }

  async function removeParty(partyId: number) {
    setErrors((e) => ({ ...e, delete: '' }))
    try {
      await deleteParty(partyId)
      setParties((prev) => prev.filter((p) => p.id !== partyId))
      if (editingPartyId === partyId) cancelEditing()
    } catch {
      setErrors((e) => ({ ...e, delete: 'Could not delete party.' }))
    }
  }

  return {
    parties,
    createDraft,
    setCreateDraft,
    editingPartyId,
    editDraft,
    setEditDraft,
    errors,
    submitCreate,
    startEditing,
    cancelEditing,
    submitEdit,
    removeParty,
  }
}
